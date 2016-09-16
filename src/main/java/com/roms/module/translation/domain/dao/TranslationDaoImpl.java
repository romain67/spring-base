package com.roms.module.translation.domain.dao;

import com.roms.library.dao.GenericDaoImplementation;
import com.roms.module.translation.domain.model.Translation;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("translationDao")
public class TranslationDaoImpl extends GenericDaoImplementation<Translation> implements TranslationDao {

    public TranslationDaoImpl() {
        super(Translation.class);
    }

    @Override
    public Translation findByCodeAndLanguage(String code, Translation.AvailableLanguage language) {
        List<Translation> translation = (List<Translation>) entityManager.createQuery(
                "SELECT t FROM Translation t WHERE t.code = :code AND t.language = :language")
                .setParameter("code", code)
                .setParameter("language", language)
                .getResultList();
        return translation.size() > 0 ? translation.get(0) : null;
    }

}
