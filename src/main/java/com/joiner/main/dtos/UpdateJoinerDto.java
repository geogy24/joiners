package com.joiner.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joiner.main.models.Joiner;
import com.joiner.main.models.LanguageLevel;
import com.joiner.main.models.Role;
import com.joiner.main.models.Stack;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Joiner dto is using to check joiner's data
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@Data
@Builder
public class UpdateJoinerDto {
    @JsonProperty(value = "identification_number")
    @Size(min = 6, max = 15)
    private String identificationNumber;

    @Size(min = 2, max = 40)
    private String name;

    @JsonProperty(value= "last_name")
    @Size(min = 2, max = 50)
    private String lastName;

    @JsonProperty(value= "domain_experience")
    private String domainExperience;

    @JsonProperty(value= "role_id")
    @Min(1)
    private Long roleId;

    @JsonProperty(value= "language_level_id")
    @Min(1)
    private Long languageLevelId;

    @JsonProperty(value= "stack_id")
    @Min(1)
    private Long stackId;

    public Joiner toJoiner(Joiner joiner) {
        return Joiner.builder()
                .id(joiner.getId())
                .identificationNumber(Objects.nonNull(this.identificationNumber) ? this.identificationNumber : joiner.getIdentificationNumber())
                .name(Objects.nonNull(this.name) ? this.name : joiner.getName())
                .lastName(Objects.nonNull(this.lastName) ? this.lastName : joiner.getLastName())
                .domainExperience(Objects.nonNull(this.domainExperience) ? this.domainExperience : joiner.getDomainExperience())
                .role(Objects.nonNull(this.roleId) ? Role.builder().id(this.roleId).build() : joiner.getRole())
                .languageLevel(Objects.nonNull(this.languageLevelId) ? LanguageLevel.builder().id(this.languageLevelId).build() : joiner.getLanguageLevel())
                .stack(Objects.nonNull(this.stackId) ? Stack.builder().id(this.stackId).build() : joiner.getStack())
                .build();
    }
}