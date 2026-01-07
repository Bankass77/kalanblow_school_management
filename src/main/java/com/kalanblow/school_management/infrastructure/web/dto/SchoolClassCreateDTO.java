package com.kalanblow.school_management.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClassCreateDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "La capacité est obligatoire")
    @Positive(message = "La capacité doit être positive")
    private Integer capacity;

    @NotNull(message = "L'établissement est obligatoire")
    private Long etablissementId;

    private Long anneeScolaireId;

}
