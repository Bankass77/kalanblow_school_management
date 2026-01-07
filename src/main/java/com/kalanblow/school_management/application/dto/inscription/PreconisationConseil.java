package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.inscription.DecisionConseil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
class PreconisationConseil implements Serializable {
    private Long studentId;
    private String nomEleve;
    private double moyenneGenerale;
    private int absences;
    private int retards;
    private DecisionConseil decisionProposee;
    private String justification;
    private List<String> matieresProblematiques;
}

