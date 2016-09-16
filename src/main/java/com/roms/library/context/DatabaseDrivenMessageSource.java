package com.roms.library.context;

import com.roms.module.translation.domain.dao.TranslationDao;
import com.roms.module.translation.domain.model.Translation;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import java.text.MessageFormat;
import java.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DatabaseDrivenMessageSource extends AbstractMessageSource {

    private TranslationDao translationDao;
    private static final Logger logger = LogManager.getLogger(DatabaseDrivenMessageSource.class);
    private Map<Translation.AvailableLanguage, Map<String, String>> translations =
            new HashMap<Translation.AvailableLanguage, Map<String, String>>();

    public DatabaseDrivenMessageSource(TranslationDao translationDao) {
        this.translationDao = translationDao;
        reload();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        MessageFormat result = createMessageFormat(getTranslation(code, locale), locale);
        return result;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getTranslation(code, locale);
    }

    public void reload() {
        logger.info("Load translations from database");
        translations.clear();
        translations.putAll(loadTranslations());
    }

    private String getTranslation(String code, Locale locale) {
        Translation.AvailableLanguage language = Translation.AvailableLanguage.fromCode(locale.getLanguage());
        if (language == null) {
            logger.error("Locale '" + locale.getLanguage() + "' was not found in available locales");
            return null;
        }

        Map<String, String> languageTranslations = translations.get(language);
        if (languageTranslations == null) {
            logger.warn("Locale '" + language.code() + "' was not found in translations");
            return null;
        }

        String translation = languageTranslations.get(code);
        if (translation == null) {
            try {
                translation = getParentMessageSource().getMessage(code, null, locale);
            } catch (NoSuchMessageException e) {
                logger.warn("Code '" + code + "' for Locale '" + language.code() + "' was not found in translations");
                return null;
            }
        }

        return translation;
    }

    private Map<Translation.AvailableLanguage, Map<String, String>> loadTranslations() {
        Map<Translation.AvailableLanguage, Map<String, String>> translations =
                new HashMap<Translation.AvailableLanguage, Map<String, String>>();
        List<Translation> allTranslations = translationDao.findAll();

        for (Translation translation : allTranslations) {
            Translation.AvailableLanguage language = translation.getLanguage();
            if (! translations.containsKey(language)) {
                translations.put(language, new HashMap<String, String>());
            }
            translations.get(language).put(translation.getCode(), translation.getValue());
        }

        return translations;
    }

}
