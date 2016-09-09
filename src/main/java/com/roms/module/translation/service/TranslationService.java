package com.roms.module.translation.service;

import com.roms.module.translation.domain.dao.TranslationDao;
import com.roms.module.translation.domain.dto.TranslationCreateDto;
import com.roms.module.translation.domain.dto.TranslationDto;
import com.roms.module.translation.domain.model.Translation;
import org.hibernate.ObjectNotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service("translationService")
public class TranslationService {

	@Autowired
	private TranslationDao translationDao;

	public void save(Translation translation) {
		this.translationDao.save(translation);
	}
	
	public Translation find(long id) {
		return this.translationDao.find(id);
	}

	public Collection<Translation> findAll() {
		return this.translationDao.findAll();
	}

    @Transactional
	public void delete(long id) {
		Translation translation = this.find(id);
		
		if (translation == null) {
			throw new ObjectNotFoundException(id, "translation");
		}
		
		translationDao.delete(translation);
	}

    @Transactional
	public Translation create(TranslationCreateDto translationDto) {
		Translation translation = new Translation();
        translation.setCode(translationDto.getCode());
        translation.setLanguage(Translation.AvailableLanguage.valueOf(translationDto.getLanguage()));
        translation.setValue(translationDto.getValue());
        translation.setCreatedAt(LocalDateTime.now());
        translation.setUpdatedAt(LocalDateTime.now());
    	this.save(translation);
		return translation;
	}

	public boolean isUnique(TranslationDto dto) {
        Translation.AvailableLanguage language = Translation.AvailableLanguage.valueOf(dto.getLanguage());
	    if (translationDao.findByCodeAndLanguage(dto.getCode(), language) == null) {
            return true;
        }
        return false;
    }

}
