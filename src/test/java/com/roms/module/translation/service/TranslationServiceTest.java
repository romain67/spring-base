package com.roms.module.translation.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.roms.config.AppConfig;
import com.roms.config.MvcConfig;
import com.roms.module.translation.domain.dto.TranslationCreateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.Locale;

import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {AppConfig.class, MvcConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TranslationServiceTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    TranslationService translationService;

    @Test
    @DatabaseSetup("sampleData.xml")
    public void messageSourceReloadAfterSave() throws Exception {
        TranslationCreateDto dto = new TranslationCreateDto();
        dto.setCode("test.code.create");
        dto.setLanguage("EN");
        dto.setValue("create");
        translationService.create(dto);

        assertEquals("create", messageSource.getMessage("test.code.create", null, Locale.ENGLISH));
    }

}