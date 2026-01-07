package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.inscription.DecisionConseil;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidationDecisionRequest {
    @NotNull
    private Long studentId;

    @NotNull
    private DecisionConseil decision;

    private String commentaire;

    @NotNull
    private LocalDate dateEffet;
}
