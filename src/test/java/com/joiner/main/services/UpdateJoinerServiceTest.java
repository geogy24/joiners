package com.joiner.main.services;

import com.github.javafaker.Faker;
import com.joiner.main.exceptions.JoinerNotFoundException;
import com.joiner.main.exceptions.LanguageLevelNotFoundException;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.exceptions.StackNotFoundException;
import com.joiner.main.factories.JoinerFactory;
import com.joiner.main.factories.LanguageLevelFactory;
import com.joiner.main.factories.RoleFactory;
import com.joiner.main.factories.StackFactory;
import com.joiner.main.repositories.JoinerRepository;
import com.joiner.main.repositories.LanguageLevelRepository;
import com.joiner.main.repositories.RoleRepository;
import com.joiner.main.repositories.StackRepository;
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
@ContextConfiguration(classes = {UpdateJoinerService.class})
public class UpdateJoinerServiceTest {

    @MockBean
    private JoinerRepository joinerRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private LanguageLevelRepository languageLevelRepository;

    @MockBean
    private StackRepository stackRepository;

    @Autowired
    private UpdateJoinerService updateJoinerService;

    private JoinerFactory joinerFactory;

    private Faker faker;

    @Before
    public void setup() {
        this.faker = new Faker();
        this.joinerFactory = new JoinerFactory();
    }

    @Test
    public void whenExecuteServiceThenReturnJoinerUpdated() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.of(this.joinerFactory.model()));
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.of(new LanguageLevelFactory().model()));
        given(this.stackRepository.findById(anyLong())).willReturn(Optional.of(new StackFactory().model()));
        given(this.joinerRepository.save(any())).willReturn(this.joinerFactory.model());

        assertThat(this.updateJoinerService.execute(id, this.joinerFactory.updateDto())).isNotNull();
    }

    @Test
    public void whenExecuteServiceButRoleNotFoundThenRaiseJoinerNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(JoinerNotFoundException.class, () -> this.updateJoinerService.execute(id, this.joinerFactory.updateDto()));
    }

    @Test
    public void whenExecuteServiceButRoleNotFoundThenRaiseRoleNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.of(this.joinerFactory.model()));
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> this.updateJoinerService.execute(id, this.joinerFactory.updateDto()));
    }

    @Test
    public void whenExecuteServiceButLanguageLevelNotFoundThenRaiseLanguageLevelNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.of(this.joinerFactory.model()));
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(LanguageLevelNotFoundException.class, () -> this.updateJoinerService.execute(id, this.joinerFactory.updateDto()));
    }

    @Test
    public void whenExecuteServiceButStackNotFoundThenRaiseStackNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.joinerRepository.findById(anyLong())).willReturn(Optional.of(this.joinerFactory.model()));
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.of(new LanguageLevelFactory().model()));
        given(this.stackRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(StackNotFoundException.class, () -> this.updateJoinerService.execute(id, this.joinerFactory.updateDto()));
    }
}
