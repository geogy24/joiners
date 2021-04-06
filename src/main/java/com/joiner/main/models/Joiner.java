package com.joiner.main.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Joiner model is using to save joiner's data
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@Entity
@Table(name = "joiners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Joiner {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 6, max = 15)
    private String identificationNumber;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @Column
    private String domainExperience;

    @ManyToOne
    private Role role;

    @ManyToOne
    private LanguageLevel languageLevel;

    @ManyToOne
    private Stack stack;
}