package com.roms.config;

import com.roms.library.context.DatabaseDrivenMessageSource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import com.roms.library.mail.sender.JavaMailSenderDry;
import com.roms.library.mail.sender.JavaMailSenderRedirect;
import com.roms.module.translation.domain.dao.TranslationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.core.env.Environment;
import java.util.Locale;
import java.util.Properties;
import org.springframework.validation.Validator;

@Configuration
@DependsOn("propertySourcesResolver")
@EnableWebMvc
@ComponentScan({"com.roms"})
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private TranslationDao translationDao;

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(environment.getRequiredProperty("i18n.locale.default")));
        localeResolver.setCookieMaxAge(
                Integer.parseInt(environment.getRequiredProperty("i18n.locale.resolver.cookie_time")));
        return localeResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource propertiesMessageSource() {
        ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setBasename("/WEB-INF/messages/messages/");
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setCacheSeconds(
                Integer.parseInt(environment.getRequiredProperty("i18n.message_source.cache_time")));
        return bundleMessageSource;
    }

    @Bean
    public DatabaseDrivenMessageSource messageSource() {
        DatabaseDrivenMessageSource messageSource = new DatabaseDrivenMessageSource(translationDao);
        messageSource.setParentMessageSource(propertiesMessageSource());
        return messageSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static resources from both WEB-INF and webjars
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(Integer.parseInt(environment.getRequiredProperty("resource.cache_time")));
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl sender;

        if (Boolean.parseBoolean(environment.getProperty("mail.dry_mode"))) { // Dry mode
            sender = new JavaMailSenderDry();
        } else if (! environment.getProperty("mail.redirect_to", "").isEmpty()) { // Redirect mode
            sender = new JavaMailSenderRedirect();
            ((JavaMailSenderRedirect)sender).setRedirectTo(environment.getProperty("mail.redirect_to"));
        } else { // Real life mode
            sender = new JavaMailSenderImpl();
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.host", environment.getRequiredProperty("mail.smtp.host"));
        properties.put("mail.smtp.socketFactory.port", environment.getRequiredProperty("mail.smtp.port"));
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", environment.getRequiredProperty("mail.smtp.port"));

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(environment.getRequiredProperty("mail.smtp.username"),
                        environment.getRequiredProperty("mail.smtp.password"));
            }
        };

        Session session = Session.getDefaultInstance(properties, auth);
        sender.setSession(session);

        return sender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(environment.getRequiredProperty("mail.default_sender"));
        return simpleMailMessage;
    }

}
