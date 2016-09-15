package com.roms.module.translation.service;

import com.roms.library.context.DatabaseDrivenMessageSource;
import com.roms.module.translation.domain.dao.TranslationDao;
import com.roms.module.translation.domain.dto.TranslationCreateDto;
import com.roms.module.translation.domain.dto.TranslationDto;
import com.roms.module.translation.domain.model.Translation;
import org.hibernate.ObjectNotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("translationService")
public class TranslationService {

	@Autowired
	private TranslationDao translationDao;

	@Autowired
	private MessageSource messageSource;

    @Transactional
	public void save(Translation translation) {
		this.translationDao.save(translation);
        reloadMessageSource();
	}
	
	public Translation find(long id) {
		return this.translationDao.find(id);
	}

	public List<Translation> findAll() {
		return this.translationDao.findAll();
	}

    @Transactional
	public void delete(long id) {
		Translation translation = this.find(id);
		
		if (translation == null) {
			throw new ObjectNotFoundException(id, "translation");
		}
		
		translationDao.delete(translation);
        reloadMessageSource();
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

    /**
     * Reload messageSource after change on database
     */
    private void reloadMessageSource() {
        if (messageSource instanceof DatabaseDrivenMessageSource) {
            ((DatabaseDrivenMessageSource)messageSource).reload();
        } else if (messageSource instanceof DelegatingMessageSource) {
            DelegatingMessageSource myMessage = (DelegatingMessageSource) messageSource;
            if (myMessage.getParentMessageSource()!= null
                    && myMessage.getParentMessageSource() instanceof DatabaseDrivenMessageSource) {
                ((DatabaseDrivenMessageSource) myMessage.getParentMessageSource()).reload();
            }
        }
    }

}
