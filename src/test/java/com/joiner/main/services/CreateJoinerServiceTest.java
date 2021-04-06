package com.joiner.main.services;

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
@ContextConfiguration(classes = {CreateJoinerService.class})
public class CreateJoinerServiceTest {

    @MockBean
    private JoinerRepository joinerRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private LanguageLevelRepository languageLevelRepository;

    @MockBean
    private StackRepository stackRepository;

    @Autowired
    private CreateJoinerService createJoinerService;

    private JoinerFactory joinerFactory;

    @Before
    public void setup() {
        this.joinerFactory = new JoinerFactory();
    }

    @Test
    public void whenExecuteServiceThenReturnJoinerCreated() {
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.of(new LanguageLevelFactory().model()));
        given(this.stackRepository.findById(anyLong())).willReturn(Optional.of(new StackFactory().model()));
        given(this.joinerRepository.save(any())).willReturn(this.joinerFactory.model());

        assertThat(this.createJoinerService.execute(this.joinerFactory.dto())).isNotNull();
    }

    @Test
    public void whenExecuteServiceButRoleNotFoundThenRaiseRoleNotFoundException() {
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> this.createJoinerService.execute(this.joinerFactory.dto()));
    }

    @Test
    public void whenExecuteServiceButLanguageLevelNotFoundThenRaiseLanguageLevelNotFoundException() {
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(LanguageLevelNotFoundException.class, () -> this.createJoinerService.execute(this.joinerFactory.dto()));
    }

    @Test
    public void whenExecuteServiceButStackNotFoundThenRaiseStackNotFoundException() {
        given(this.roleRepository.findById(anyLong())).willReturn(Optional.of(new RoleFactory().model()));
        given(this.languageLevelRepository.findById(anyLong())).willReturn(Optional.of(new LanguageLevelFactory().model()));
        given(this.stackRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(StackNotFoundException.class, () -> this.createJoinerService.execute(this.joinerFactory.dto()));
    }
}
