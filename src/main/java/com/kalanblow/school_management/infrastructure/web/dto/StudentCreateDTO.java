package com.kalanblow.school_management.infrastructure.web.dto;

import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.enums.EtatEleve;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class StudentCreateDTO {
    @NotNull
    private User user;

    @NotBlank
    private String ineNumber;

    @NotNull
    private LocalDate dateDeNaissance;

    private Long schoolClassId;

    @NotNull
    private Long etablissementId;

    private Set<Long> parentIds;

    private EtatEleve etat;

    private Set<Long> anneeScolaireIds; // pour historiqueScolaires
}
