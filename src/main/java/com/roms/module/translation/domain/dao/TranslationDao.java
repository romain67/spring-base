package com.roms.module.translation.domain.dao;

import com.roms.library.dao.GenericDaoInterface;
import com.roms.module.translation.domain.model.Translation;

public interface TranslationDao extends GenericDaoInterface<Translation> {

    Translation findByCodeAndLanguage(String code, Translation.AvailableLanguage language);

}
