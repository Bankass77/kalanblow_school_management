package com.kalanblow.school_management.application.dto.student;

import com.kalanblow.school_management.model.shared.User;
import com.kalanblow.school_management.model.shared.enums.EtatEleve;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record StudentRequest(@NotBlank User user, LocalDate dateDeNaissance, Long schoolClassId,
                             @NotNull Long etablissementI, Set<Long> parentIds, EtatEleve etat,
                             Set<Long> anneeScolaireIds,Set<Long> historiqueScolaireIds) {
}
