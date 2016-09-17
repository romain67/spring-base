package com.roms.module.translation.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import static org.junit.Assert.*;

public class TranslationServiceTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    TranslationService translationService;

    @Test
    public void messageSourceReloadAfterSave() throws Exception {

    }

}