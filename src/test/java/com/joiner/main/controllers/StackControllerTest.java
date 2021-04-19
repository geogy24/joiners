package com.joiner.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.factories.StackFactory;
import com.joiner.main.models.Stack;
import com.joiner.main.services.interfaces.ShowStackService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StackController.class)
@ContextConfiguration(classes = {StackController.class})
public class StackControllerTest {
    private final static String SEARCH_STACK_URL = "/api/stacks/{id}";
    private final static String UTF_8_KEY = "utf-8";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowStackService showStackService;

    private Stack stackModel;

    private HashMap<String, Object> stackMap;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Faker faker;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.faker = new Faker();

        StackFactory stackFactory = new StackFactory();
        this.stackMap = stackFactory.map();
        this.stackModel = stackFactory.model(this.stackMap);
    }

    @Test
    public void whenSearchAStackThenReturnsStackFound() throws Exception {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.showStackService.execute(anyLong())).willReturn(this.stackModel);

        this.mockMvc
                .perform(get(SEARCH_STACK_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.stackMap)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = MockitoException.class)
    public void whenSearchAStackButNotFoundThenReturnsStackNotFoundException() throws Exception {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        when(this.showStackService.execute(anyLong())).thenThrow(StackNotFoundException.class);

        this.mockMvc
                .perform(get(SEARCH_STACK_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.stackMap)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = AssertionError.class)
    public void whenSearchAStackButSendNotValidDataThenReturnsBadRequestException() throws Exception {
        String id = this.faker.lorem().word();
        when(this.showStackService.execute(anyLong())).thenThrow(HttpClientErrorException.BadRequest.class);

        this.mockMvc
                .perform(get(SEARCH_STACK_URL, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.stackMap)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
