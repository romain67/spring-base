package com.roms.module.user.service;

import com.roms.library.exception.RegisterErrorException;
import com.roms.library.format.StringFormat;
import com.roms.library.http.exception.NotFoundException;
import com.roms.library.security.AutoLogin;
import com.roms.module.user.domain.dao.RoleDao;
import com.roms.module.user.domain.dto.UserRegisterDto;
import com.roms.module.user.domain.model.Role;
import com.roms.module.user.domain.model.User;
import freemarker.template.Configuration;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.internet.MimeMessage;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@Service("accountService")
public class AccountService {

    private static final Logger logger = LogManager.getLogger(AccountService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private AutoLogin autoLogin;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    @PreAuthorize("isAnonymous()")
	public User register(UserRegisterDto registerDto) throws RegisterErrorException {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setUsernameCanonical(StringFormat.canonicalize(registerDto.getUsername()));
        user.setEmail(registerDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(registerDto.getPassword()));
        user.setActive(0);
        user.setCreatedAt(LocalDateTime.now());
        user.addRole(roleDao.findByName(Role.ROLE_USER));
        String token = makeToken(user);
        user.setToken(token);
        userService.save(user);

        try {
            sendActivationEmail(user, registerDto.getActivationUrl());
        } catch (Exception e) {
            throw new RegisterErrorException(e.getMessage());
        }

        return user;
	}

    @Transactional
    public void activate(String token) {
        User user = userService.findByToken(token);
        if (user == null) {
            throw new NotFoundException("Can not found user with token '" + token + "'");
        }

        user.setActive(1);
        user.setToken(null);
        userService.save(user);
        autoLogin.authenticateUserAndInitializeSession(user);
    }

	private void sendActivationEmail(final User user, final String activationUrl) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                Locale locale = LocaleContextHolder.getLocale();
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setTo(user.getEmail());
                message.setSubject(messageSource.getMessage("account.register.mail.subject", null, locale));

                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("username", user.getUsername());
                model.put("activationUrl", activationUrl.replace("{token}", user.getToken()));
                model.put("locale", locale);

                String content = FreeMarkerTemplateUtils.processTemplateIntoString(
                        freemarkerConfiguration.getTemplate("mail/account-activation.ftl", "UTF-8"), model);

                message.setText(content, true);
            }
        };

        this.mailSender.send(preparator);
    }

	private String makeToken(User user) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            String baseString = user.getEmail() +
                    LocalDateTime.now().toString() +
                    RandomStringUtils.randomAlphanumeric(10);
            byte[] md5 = digest.digest(baseString.getBytes("UTF-8"));
            return new BigInteger(1 , md5).toString(16);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

}
