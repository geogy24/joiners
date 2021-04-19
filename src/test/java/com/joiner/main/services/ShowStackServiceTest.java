package com.joiner.main.services;

import com.github.javafaker.Faker;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.factories.StackFactory;
import com.joiner.main.repositories.StackRepository;
import com.joiner.main.services.implementations.ShowStackService;
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
@ContextConfiguration(classes = {ShowStackService.class})
public class ShowStackServiceTest {

    @MockBean
    private StackRepository stackRepository;

    @Autowired
    private ShowStackService showStackService;

    private StackFactory stackFactory;

    private Faker faker;

    @Before
    public void setup() {
        this.faker = new Faker();
        this.stackFactory = new StackFactory();
    }

    @Test
    public void whenExecuteServiceThenReturnStackSearched() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.stackRepository.findById(any())).willReturn(Optional.of(this.stackFactory.model()));

        assertThat(this.showStackService.execute(id)).isNotNull();
    }

    @Test
    public void whenExecuteServiceButStackNotFoundThenRaiseStackNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.stackRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(StackNotFoundException.class, () -> this.showStackService.execute(id));
    }
}
