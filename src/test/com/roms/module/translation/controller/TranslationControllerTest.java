package com.roms.module.translation.controller;

import com.roms.config.AppConfig;
import com.roms.config.MvcConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {AppConfig.class, MvcConfig.class})
public class TranslationControllerTest {

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
    public void testGetAll() throws Exception {
        this.mockMvc.perform(get("/translation").with(anonymous()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        this.mockMvc.perform(get("/translation/7").with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":7,\"code\":\"error.translation.invalid_language\"," +
                        "\"language\":\"EN\",\"value\":\"Invalid language\",\"createdAt\":\"2016-09-15 01:20:20\"," +
                        "\"updatedAt\":\"2016-09-15 01:20:20\"}"));
    }

}