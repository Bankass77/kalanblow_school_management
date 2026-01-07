package com.kalanblow.school_management.application.dto.sanction;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SanctionResponse(String id,
                               String studentId,
                               String studentNom,
                               String typeSanction,
                               String gravite,
                               String motif,
                               String description,
                               LocalDate dateSanction,
                               LocalDate dateDebut,
                               LocalDate dateFin,
                               LocalDate dateLevee,
                               boolean appliquee,
                               boolean contestee,
                               String motifContestation,
                               LocalDateTime dateContestation,
                               boolean notifierParents,
                               String auteurSanction,
                               String signataire,
                               boolean enCours) {

}
