package com.roms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.core.env.Environment;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.roms" })
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final int CACHE_PERIOD = 31556926; // One year

    @Autowired
    private Environment environment;

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(environment.getRequiredProperty("i18n.locale.default")));
        return localeResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static resources from both WEB-INF and webjars
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(CACHE_PERIOD);
    }

}
