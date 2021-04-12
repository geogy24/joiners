package com.joiner.main.models;

import com.github.javafaker.Faker;
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
public class LanguageLevelTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private Faker faker;

    @Before
    public void setUp() {
        this.faker = new Faker();
    }

    @Test
    public void whenSaveLanguageLevelThenLanguageLevelShouldFound() {
        LanguageLevel languageLevel = new LanguageLevel();
        languageLevel.setLanguage(this.faker.lorem().word());
        LanguageLevel savedLanguageLevel = this.testEntityManager.persistFlushFind(languageLevel);

        assertThat(savedLanguageLevel.getId()).isNotNull();
        assertThat(savedLanguageLevel.getLanguage()).isEqualTo(languageLevel.getLanguage());
    }
}
