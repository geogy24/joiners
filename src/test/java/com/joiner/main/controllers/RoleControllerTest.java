package com.joiner.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.factories.RoleFactory;
import com.joiner.main.models.Role;
import com.joiner.main.services.interfaces.ShowRoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
@ContextConfiguration(classes = {RoleController.class})
public class RoleControllerTest {
    private final static String SEARCH_ROLES_URL = "/api/roles?id={id}";
    private final static String UTF_8_KEY = "utf-8";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowRoleService showRoleService;

    private Role roleModel;

    private HashMap<String, Object> roleMap;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Faker faker;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.faker = new Faker();

        RoleFactory roleFactory = new RoleFactory();
        this.roleMap = roleFactory.map();
        this.roleModel = roleFactory.model(this.roleMap);
    }

    @Test
    public void whenSearchARoleThenReturnsRoleFound() throws Exception {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.showRoleService.execute(any())).willReturn(List.of(this.roleModel));

        this.mockMvc
                .perform(get(SEARCH_ROLES_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.roleMap)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = MockitoException.class)
    public void whenSearchARoleButNotFoundThenReturnsRoleNotFoundException() throws Exception {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        when(this.showRoleService.execute(any())).thenThrow(RoleNotFoundException.class);

        this.mockMvc
                .perform(get(SEARCH_ROLES_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.roleMap)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = AssertionError.class)
    public void whenSearchARoleButSendNotValidDataThenReturnsBadRequestException() throws Exception {
        String id = this.faker.lorem().word();
        when(this.showRoleService.execute(any())).thenThrow(HttpClientErrorException.BadRequest.class);

        this.mockMvc
                .perform(get(SEARCH_ROLES_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.roleMap)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
