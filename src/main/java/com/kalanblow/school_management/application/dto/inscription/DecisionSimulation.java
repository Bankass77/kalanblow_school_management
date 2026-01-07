package com.kalanblow.school_management.application.dto.inscription;

import com.kalanblow.school_management.model.inscription.DecisionConseil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
class DecisionSimulation {
    private Long studentId;
    private String nomEleve;
    private DecisionConseil decisionSimulee;
    private Map<String, Object> criteres;
    private List<DecisionConseil> alternatives;
    private String recommandation;
}
