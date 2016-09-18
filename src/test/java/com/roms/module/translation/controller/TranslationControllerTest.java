package com.roms.module.translation.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.roms.config.AppConfig;
import com.roms.config.MvcConfig;
import com.roms.library.controller.error.dto.RestErrorMessageDto;
import com.roms.library.controller.error.dto.RestFieldErrorsDto;
import com.roms.module.translation.domain.dto.TranslationCreateDto;
import com.roms.module.translation.domain.model.Translation;
import com.roms.module.translation.service.TranslationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.roms.library.test.TestUtil;
import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {AppConfig.class, MvcConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TranslationControllerTest {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public  void downUp(){
        this.mockMvc = null;
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    public void testGetAll() throws Exception {
        this.mockMvc.perform(get("/translation").with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"code\":\"test.code\"," + "\"language\":\"EN\"," +
                        "\"value\":\"translation en\",\"createdAt\":\"2016-09-15 01:20:20\"," +
                        "\"updatedAt\":\"2016-09-15 01:20:20\"},{\"id\":2,\"code\":\"test.code\"," +
                        "\"language\":\"FR\",\"value\":\"translation fr\",\"createdAt\":\"2016-09-15 01:20:20\"," +
                        "\"updatedAt\":\"2016-09-15 01:20:20\"}]"));
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    public void testGet() throws Exception {
        this.mockMvc.perform(get("/translation/1").with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"code\":\"test.code\"," + "\"language\":\"EN\"," +
                        "\"value\":\"translation en\",\"createdAt\":\"2016-09-15 01:20:20\"," +
                        "\"updatedAt\":\"2016-09-15 01:20:20\"}"));
    }

    @Test
    public void testPostAsAnonymous() throws Exception {
        this.mockMvc.perform(post("/translation").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testPostCodeTooLong() throws Exception {
        TranslationCreateDto dto = makeTranslationCreateDto(TestUtil.createStringWithLength(101), "EN", "prout");
        MvcResult result = this.mockMvc.perform(post("/translation").with(user("user").roles("TRANSLATOR"))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andReturn();

        String resultBody = result.getResponse().getContentAsString();

        RestErrorMessageDto error = TestUtil.convertJsonStringToObject(resultBody, RestErrorMessageDto.class);
        assertEquals(error.getErrorCode(), "invalid_value");

        ArrayList<RestFieldErrorsDto> errors = error.getErrors();
        assertEquals(errors.get(0).getErrorCode(), "Length");
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    public void testPost() throws Exception {
        TranslationCreateDto dto = makeTranslationCreateDto("test.code.create", "EN", "prout");
        this.mockMvc.perform(post("/translation").with(user("user").roles("TRANSLATOR"))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        Translation record = translationService.findByCodeAndLanguage("test.code.create",
                Translation.AvailableLanguage.EN);
        assertNotNull(record);
    }

    private TranslationCreateDto makeTranslationCreateDto(String code, String language, String value) {
        TranslationCreateDto dto = new TranslationCreateDto();
        dto.setCode(code);
        dto.setLanguage(language);
        dto.setValue(value);
        return dto;
    }

}
