package com.joiner.main.services;

import com.github.javafaker.Faker;
import com.joiner.main.exceptions.JoinerNotFoundException;
import com.joiner.main.factories.JoinerFactory;
import com.joiner.main.repositories.JoinerRepository;
import com.joiner.main.services.implementations.ShowJoinerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShowJoinerService.class})
public class ShowJoinerServiceTest {

    @MockBean
    private JoinerRepository joinerRepository;

    @Autowired
    private ShowJoinerService showJoinerService;

    private JoinerFactory joinerFactory;

    private Faker faker;

    @Before
    public void setup() {
        this.faker = new Faker();
        this.joinerFactory = new JoinerFactory();
    }

    @Test
    public void whenExecuteServiceThenReturnJoinerSearched() {
        Long id = Long.valueOf(this.faker.number().numberBetween(1, 10));
        given(this.joinerRepository.findById(any())).willReturn(Optional.of(this.joinerFactory.model()));

        assertThat(this.showJoinerService.execute(id)).isNotNull();
    }

    @Test
    public void whenExecuteServiceButJoinerNotFoundThenRaiseJoinerNotFoundException() {
        Long id = Long.valueOf(this.faker.number().numberBetween(1, 10));
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(JoinerNotFoundException.class, () -> this.showJoinerService.execute(id));
    }
}
