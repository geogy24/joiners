package com.joiner.main.factories;

import com.github.javafaker.Faker;
import com.joiner.main.dtos.JoinerDto;
import com.joiner.main.models.Joiner;

import java.util.HashMap;

public class JoinerFactory {
    private final static String IDENTIFICATION_NUMBER_KEY = "identification_number";
    private final static String NAME_KEY = "name";
    private final static String LAST_NAME_KEY = "last_name";
    private final static String DOMAIN_EXPERIENCE_KEY = "domain_experience";
    private final static String ROLE_ID_KEY = "role_id";
    private final static String LANGUAGE_LEVEL_ID_KEY = "language_level_id";
    private final static String STACK_ID_KEY = "stack_id";

    private Faker faker;

    public JoinerFactory() {
        this.faker = new Faker();
    }

    public HashMap<String, Object> map() {
        return new HashMap<>() {{
            put(IDENTIFICATION_NUMBER_KEY, faker.idNumber().valid());
            put(NAME_KEY, faker.name().firstName());
            put(LAST_NAME_KEY, faker.name().lastName());
            put(DOMAIN_EXPERIENCE_KEY, faker.lorem().sentence());
            put(ROLE_ID_KEY, faker.number().digits(3));
            put(LANGUAGE_LEVEL_ID_KEY, faker.number().digits(3));
            put(STACK_ID_KEY, faker.number().digits(3));
        }};
    }

    public Joiner model(HashMap<String, Object> joinerMap) {
        return Joiner.builder()
                .identificationNumber(joinerMap.get(IDENTIFICATION_NUMBER_KEY).toString())
                .name(joinerMap.get(NAME_KEY).toString())
                .lastName(joinerMap.get(LAST_NAME_KEY).toString())
                .domainExperience(joinerMap.get(DOMAIN_EXPERIENCE_KEY).toString())
                .build();
    }

    public Joiner model() {
        return Joiner.builder()
                .identificationNumber(this.faker.idNumber().valid())
                .name(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .domainExperience(this.faker.lorem().sentence())
                .build();
    }

    public JoinerDto dto() {
        return JoinerDto.builder()
                .identificationNumber(faker.idNumber().valid())
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .domainExperience(faker.lorem().sentence())
                .roleId(Long.parseLong(faker.number().digits(3)))
                .languageLevelId(Long.parseLong(faker.number().digits(3)))
                .stackId(Long.parseLong(faker.number().digits(3)))
                .build();
    }
}
