package com.joiner.main.factories;

import com.github.javafaker.Faker;
import com.joiner.main.models.Role;

public class RoleFactory {
    private Faker faker;

    public RoleFactory() {
        this.faker = new Faker();
    }

    public Role model() {
        return Role.builder()
                .id(Long.parseLong(this.faker.number().digits(3)))
                .name(this.faker.lorem().word())
                .build();
    }
}
