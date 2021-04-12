package com.joiner.main.models;

import com.github.javafaker.Faker;
import com.joiner.main.factories.JoinerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class JoinerTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private Faker faker;

    @Before
    public void setUp() {
        this.faker = new Faker();
    }

    @Test
    public void whenSaveJoinerThenJoinerShouldFound() {
        Stack stack = new Stack();
        stack.setName(this.faker.lorem().word());
        Stack savedStack = this.testEntityManager.persistAndFlush(stack);

        LanguageLevel languageLevel = new LanguageLevel();
        languageLevel.setLanguage(this.faker.lorem().word());
        LanguageLevel savedLanguageLevel = this.testEntityManager.persistAndFlush(languageLevel);

        Role role = new Role();
        role.setName(this.faker.lorem().word());
        Role savedRole = this.testEntityManager.persistFlushFind(role);

        JoinerFactory joinerFactory = new JoinerFactory();
        Joiner joiner = joinerFactory.model();
        joiner.setRole(savedRole);
        joiner.setStack(savedStack);
        joiner.setLanguageLevel(savedLanguageLevel);

        Joiner savedJoiner = this.testEntityManager.persistFlushFind(joiner);

        assertThat(savedJoiner.getId()).isNotNull();
        assertThat(savedJoiner.getName()).isEqualTo(joiner.getName());
        assertThat(savedJoiner.getLastName()).isEqualTo(joiner.getLastName());
        assertThat(savedJoiner.getDomainExperience()).isEqualTo(joiner.getDomainExperience());
        assertThat(savedJoiner.getLanguageLevel().getLanguage()).isEqualTo(joiner.getLanguageLevel().getLanguage());
        assertThat(savedJoiner.getRole().getName()).isEqualTo(joiner.getRole().getName());
        assertThat(savedJoiner.getStack().getName()).isEqualTo(joiner.getStack().getName());
    }
}
