package com.joiner.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joiner.main.models.Joiner;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Joiner dto is using to check joiner's data
 *
 * @author Jorge Díaz
 * @version 1.0.0
 */
@Data
@Builder
public class JoinerDto {
    @JsonProperty(value = "identification_number")
    @NotBlank
    @Size(min = 6, max = 15)
    private String identificationNumber;

    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @JsonProperty(value= "last_name")
    @NotBlank
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

    public Joiner toJoiner() {
        return Joiner.builder()
                .identificationNumber(this.identificationNumber)
                .name(this.name)
                .lastName(this.lastName)
                .domainExperience(this.domainExperience)
                .build();
    }
}