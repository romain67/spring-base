package com.roms.module.translation.controller;

import com.roms.library.http.exception.NotFoundException;
import com.roms.module.translation.domain.dto.TranslationCreateDto;
import com.roms.module.translation.domain.model.Translation;
import com.roms.module.translation.service.TranslationService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController("translationController")
@RequestMapping("/translation")
public class TranslationController {

    @Autowired
	private TranslationService translationService;

    /**
     * List translations
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Translation> getAll() {
    	return translationService.findAll();
    }

    /**
     * Get translation
     * @param id
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Translation> get(@PathVariable("id") long id) {

		Translation translation = this.translationService.find(id);

		if (translation == null) {
			throw new NotFoundException("Translation id '" + id + "' can not be found");
		}

		return new ResponseEntity<Translation>(translation, HttpStatus.OK);
	}

    /**
     * Create translation
     * @param translationDto
     */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Translation> post(@Validated @RequestBody TranslationCreateDto translationDto) {
		Translation translation = translationService.create(translationDto);
		return new ResponseEntity<Translation>(translation, HttpStatus.OK);
	}

    /**
     * Update Translation
     * @param translationDto
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void put(@Validated @RequestBody TranslationCreateDto translationDto) {
		
	}

    /**
     * Delete Translation
     * @param id
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		try {
			translationService.delete(id);
		} catch (ObjectNotFoundException e) {
			throw new NotFoundException("Translation id '" + id + "' can not be found");
		}
	}
		
}
