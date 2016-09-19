package com.roms.module.user.service;

import com.roms.library.exception.RegisterErrorException;
import com.roms.library.format.StringFormat;
import com.roms.library.http.exception.NotFoundException;
import com.roms.library.security.AutoLogin;
import com.roms.module.user.domain.dao.RoleDao;
import com.roms.module.user.domain.dto.UserRegisterDto;
import com.roms.module.user.domain.model.Role;
import com.roms.module.user.domain.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.security.MessageDigest;
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
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private AutoLogin autoLogin;

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
        } catch (MailException e) {
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

	private void sendActivationEmail(User user, String activationUrl) throws MailException {
        activationUrl = activationUrl.replace("{token}", user.getToken());
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(user.getEmail());
        msg.setText("Dear " + user.getUsername() + ", \n" +
                "Your activation link: "+ activationUrl);

        this.mailSender.send(msg);
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
