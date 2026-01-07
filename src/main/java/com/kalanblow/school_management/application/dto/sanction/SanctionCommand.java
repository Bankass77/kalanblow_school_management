package com.kalanblow.school_management.application.dto.sanction;

import com.kalanblow.school_management.model.shared.enums.TypeSanction;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SanctionCommand(Long studentId,
                              @NotNull String studentNom,
                              @NotNull String typeSanction,
                              @NotNull String gravite,
                              @NotNull String motif,
                              @NotNull String description,
                              @NotNull LocalDate dateSanction,
                              @NotNull LocalDate dateDebut,
                              @NotNull LocalDate dateFin,
                              LocalDate dateLevee,
                              boolean appliquee,
                              boolean contestee,
                              String motifContestation,
                              LocalDateTime dateContestation,
                              @NotNull boolean notifierParents,
                              @NotNull String auteurSanction,
                              @NotNull String signataire,
                              boolean enCours) {
}
