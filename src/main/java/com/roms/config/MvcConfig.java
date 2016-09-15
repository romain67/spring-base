package com.roms.config;

import com.roms.library.context.DatabaseDrivenMessageSource;
import com.roms.module.translation.domain.dao.TranslationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.core.env.Environment;
import java.util.Locale;
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
        messageSource.setUseCodeAsDefaultMessage(Boolean.parseBoolean(
                environment.getRequiredProperty("i18n.message_source.use_code_as_default_message")
        ));
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

}
