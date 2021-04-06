package com.joiner.main.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Language level model is using to save language level's data
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@Entity
@Table(name = "language_levels")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class LanguageLevel {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    @NotBlank
    private String language;
}