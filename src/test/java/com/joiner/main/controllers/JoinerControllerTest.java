package com.joiner.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joiner.main.exceptions.LanguageLevelNotFoundException;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.factories.JoinerFactory;
import com.joiner.main.models.Joiner;
import com.joiner.main.services.CreateJoinerServiceInterface;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JoinerController.class)
@ContextConfiguration(classes = {JoinerController.class})
public class JoinerControllerTest {

    private final static String JOINER_URL = "/api/joiners";
    private final static String UTF_8_KEY = "utf-8";
    private final static String STACK_ID_KEY = "stack_id";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateJoinerServiceInterface createJoinerServiceInterface;

    private Joiner joinerModel;

    private HashMap<String, Object> joinerMap;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        JoinerFactory joinerFactory = new JoinerFactory();
        this.joinerMap = joinerFactory.map();
        this.joinerModel = joinerFactory.model(this.joinerMap);
    }

    @Test
    public void whenCreatesAJoinerThenReturnsJoinerCreated() throws Exception {
        given(this.createJoinerServiceInterface.execute(any())).willReturn(this.joinerModel);

        this.mockMvc
            .perform(post(JOINER_URL)
                         .contentType(MediaType.APPLICATION_JSON)
                         .characterEncoding(UTF_8_KEY)
                         .content(this.objectMapper.writeValueAsString(this.joinerMap)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = MockitoException.class)
    public void whenCreatesAJoinerButRoleNotFoundThenReturnsNotFoundException()
        throws Exception {
        when(this.createJoinerServiceInterface.execute(any())).thenThrow(RoleNotFoundException.class);

        this.mockMvc
            .perform(post(JOINER_URL)
                         .contentType(MediaType.APPLICATION_JSON)
                         .characterEncoding(UTF_8_KEY)
                         .content(this.objectMapper.writeValueAsString(this.joinerMap)))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = MockitoException.class)
    public void whenCreatesAJoinerButLanguageLevelNotFoundThenReturnsNotFoundException()
            throws Exception {
        when(this.createJoinerServiceInterface.execute(any())).thenThrow(LanguageLevelNotFoundException.class);

        this.mockMvc
                .perform(post(JOINER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.joinerMap)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = MockitoException.class)
    public void whenCreatesAJoinerButStackNotFoundThenReturnsNotFoundException()
            throws Exception {
        when(this.createJoinerServiceInterface.execute(any())).thenThrow(StackNotFoundException.class);

        this.mockMvc
                .perform(post(JOINER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(this.joinerMap)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = java.lang.AssertionError.class)
    public void whenCreatesAJoinerButSendInvalidDataThenReturnsNotFoundException()
            throws Exception {
        HashMap<String, Object> map = this.joinerMap;
        map.put(STACK_ID_KEY, 0);
        when(this.createJoinerServiceInterface.execute(any())).thenThrow(HttpClientErrorException.BadRequest.class);

        this.mockMvc
                .perform(post(JOINER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test(expected = NestedServletException.class)
    public void whenCreatesAJoinerButStackNotFoundThenReturnsInternalServerException()
            throws Exception {
        HashMap<String, Object> map = this.joinerMap;
        map.remove(STACK_ID_KEY);
        when(this.createJoinerServiceInterface.execute(any())).thenThrow(HttpServerErrorException.InternalServerError.class);

        this.mockMvc
                .perform(post(JOINER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(UTF_8_KEY)
                        .content(this.objectMapper.writeValueAsString(map)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}