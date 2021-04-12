package com.joiner.main.factories;

import com.github.javafaker.Faker;
import com.joiner.main.models.LanguageLevel;

public class LanguageLevelFactory {
    private Faker faker;

    public LanguageLevelFactory() {
        this.faker = new Faker();
    }

    public LanguageLevel model() {
        return LanguageLevel.builder()
                .id(Long.parseLong(this.faker.number().digits(3)))
                .language(this.faker.lorem().word())
                .build();
    }
}
