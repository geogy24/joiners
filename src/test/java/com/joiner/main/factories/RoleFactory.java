package com.joiner.main.factories;

import com.github.javafaker.Faker;
import com.joiner.main.models.Role;
import com.joiner.main.models.Stack;

import java.util.HashMap;

public class RoleFactory {
    private final static String NAME_KEY = "name";
    private final static String ID_KEY = "id";

    private Faker faker;

    public RoleFactory() {
        this.faker = new Faker();
    }

    public HashMap<String, Object> map() {
        return new HashMap<>() {{
            put(ID_KEY, faker.number().digits(3));
            put(NAME_KEY, faker.name().firstName());
        }};
    }

    public Role model() {
        return Role.builder()
                .id(Long.parseLong(this.faker.number().digits(3)))
                .name(this.faker.lorem().word())
                .build();
    }

    public Role model(HashMap<String, Object> stackMap) {
        return Role.builder()
                .id(Long.parseLong(stackMap.get(ID_KEY).toString()))
                .name(stackMap.get(NAME_KEY).toString())
                .build();
    }
}
