package com.roms.config;

import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.HashMap;

@Configuration
public class AppConfig {

    private static final String DEFAULT_ENVIRONMENT_NAME = "production";
    private static final String CONFIG_FILE = "config/config.yml";
    private static final String LOCAL_CONFIG_FILE = "config/local.config.yml";

    @Autowired
    private StandardServletEnvironment environment;

    @PostConstruct
    public void init() {
        this.defineCurrentActiveEnv();
    }

    @Bean(name="propertySourcesResolver")
    public PropertySources properties() throws IOException {

        MutablePropertySources propertySources = this.environment.getPropertySources();

        // Load config for current env from config.yml
        YamlPropertiesFactoryBean yamlDefault = getYamlPropertiesFactoryBeanByEnv(CONFIG_FILE,
                DEFAULT_ENVIRONMENT_NAME);
        propertySources.addFirst(new PropertiesPropertySource("configDefault", yamlDefault.getObject()));

        // Load config to override default env if different
        String activeProfile = this.environment.getActiveProfiles()[0];
        if (DEFAULT_ENVIRONMENT_NAME.compareTo(activeProfile) != 0) {
            YamlPropertiesFactoryBean yamlCurrentEnv = getYamlPropertiesFactoryBeanByEnv(CONFIG_FILE, activeProfile);
            propertySources.addFirst(new PropertiesPropertySource("configCurrent", yamlCurrentEnv.getObject()));
        }

        // Load local config from local.config.yml
        YamlPropertiesFactoryBean yamlLocal = getYamlPropertiesFactoryBeanByEnv(LOCAL_CONFIG_FILE, activeProfile);
        propertySources.addFirst(new PropertiesPropertySource("configLocal", yamlLocal.getObject()));

        return propertySources;
    }

    /**
     * Set current environment
     */
    private void defineCurrentActiveEnv() {
        String activeProfile = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
        environment.setActiveProfiles(activeProfile);
    }

    /**
     * Read yaml config file to extract asked environment config
     *
     * @param resourcePath
     * @param environmentName
     * @return YamlPropertiesFactoryBean for asked environment
     * @throws IOException
     */
    private YamlPropertiesFactoryBean getYamlPropertiesFactoryBeanByEnv(String resourcePath, String environmentName)
            throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        Yaml yamlFile = new Yaml();
        HashMap<String, Object> configByEnv = (HashMap<String, Object>) yamlFile.load(
                new FileInputStream(classPathResource.getFile()));
        HashMap<String, String> configMap = (HashMap<String, String>) configByEnv.get(environmentName);

        Yaml generatedYaml = new Yaml();
        String defaultConfigYamlString = generatedYaml.dump(configMap);

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ByteArrayResource(defaultConfigYamlString.getBytes()));

        return yamlPropertiesFactoryBean;
    }

}
