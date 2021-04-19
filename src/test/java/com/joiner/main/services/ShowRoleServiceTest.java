package com.joiner.main.services;

import com.github.javafaker.Faker;
import com.joiner.main.exceptions.RoleNotFoundException;
import com.joiner.main.factories.RoleFactory;
import com.joiner.main.models.Role;
import com.joiner.main.repositories.RoleRepository;
import com.joiner.main.services.implementations.ShowRoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ShowRoleService.class})
public class ShowRoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ShowRoleService showRoleService;

    private RoleFactory roleFactory;

    private Faker faker;

    @Before
    public void setup() {
        this.faker = new Faker();
        this.roleFactory = new RoleFactory();
    }

    @Test
    public void whenExecuteServiceThenReturnRoleSearched() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.roleRepository.findAllById(any())).willReturn(new ArrayList<>() {{
            add(roleFactory.model());
        }});

        assertThat(this.showRoleService.execute(new Long[]{id})).isNotNull();
    }

    @Test
    public void whenExecuteServiceButRoleNotFoundThenRaiseRoleNotFoundException() {
        Long id = (long) this.faker.number().numberBetween(1, 10);
        given(this.roleRepository.findAllById(any())).willReturn(new ArrayList<>());

        assertThrows(RoleNotFoundException.class, () -> this.showRoleService.execute(new Long[]{id}));
    }
}
