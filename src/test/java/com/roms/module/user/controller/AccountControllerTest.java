package com.roms.module.user.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.roms.config.AppConfig;
import com.roms.config.MvcConfig;
import com.roms.library.test.TestUtil;
import com.roms.module.user.domain.dto.UserRegisterDto;
import com.roms.module.user.domain.model.Role;
import com.roms.module.user.domain.model.User;
import com.roms.module.user.service.UserService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {AppConfig.class, MvcConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class AccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    public void register() throws Exception {
        UserRegisterDto registerDto = makeUserRegisterDto("romain", "seignez", "Roms", "roms@romain.com", "Welcome123",
                "Welcome123", "http://www.prout.com/activate/{token}");

        this.mockMvc.perform(post("/account/register").with(anonymous())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(registerDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

        User expectedUser = userService.findByEmail("roms@romain.com");
        assertNotNull(expectedUser);

        Collection<Role> roles = expectedUser.getRoles();
        assertTrue(roles.iterator().hasNext());
        assertEquals("ROLE_USER", roles.iterator().next().getName());
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    @ExpectedDatabase(value = "ExpectedDataAfterActiveUser.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void activate() throws Exception {
        this.mockMvc.perform(get("/account/activate/03ab4db1d78048bafd886728ea7fcc8a").with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("philmau@marl.com"));
    }

    @Test
    @DatabaseSetup("sampleData.xml")
    public void myUser() throws Exception {
        this.mockMvc.perform(get("/account/my-user").with(user("odile@deray.nul")))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"firstName\":\"Odile\",\"lastName\":\"Deray\"," +
                        "\"username\":\"odile\",\"usernameCanonical\":\"odile\",\"email\":\"odile@deray.nul\"," +
                        "\"active\":1,\"createdAt\":\"2016-09-15 01:20:20\",\"lastLogin\":\"2016-09-16 01:20:20\"," +
                        "\"roles\":[{\"name\":\"ROLE_USER\"}]}"));
    }

    private UserRegisterDto makeUserRegisterDto(String firstName, String lastName, String username, String email,
                                                String password, String passwordConfirm, String activationUrl) {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirm(passwordConfirm);
        dto.setActivationUrl(activationUrl);
        return dto;
    }

}