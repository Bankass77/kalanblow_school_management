package com.kalanblow.school_management.model.inscription;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum DecisionConseil {
    ADMISSION("Admission en classe supérieure"),
    ADMISSION_SOUS_CONDITION("Admission sous condition"),
    REDOUBLEMENT("Redoublement de la classe"),
    REDOUBLEMENT_AVEC_DEROGATION("Redoublement avec dérogation"),
    ORIENTATION("Orientation vers une autre filière"),
    EXCLUSION_TEMPORAIRE("Exclusion temporaire"),
    EXCLUSION_DEFINITIVE("Exclusion définitive"),
    AJOURNEMENT("Ajournement - Décision reportée"),
    CONSEIL_DE_PROFESSEURS("Soumis au conseil de professeurs"),
    COMMISSION_EDUCATIVE("Saisine de la commission éducative"),
    PAS_DE_DECISION("Aucune décision prise"),
    RATTRAPAGE(" rattrapage"),
    AUTRE("Autre décision");

    private final String libelle;

    DecisionConseil(String libelle) {
        this.libelle = libelle;
    }

    @JsonValue
    public String getLibelle() {
        return libelle;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DecisionConseil fromLibelle(String libelle) {
        for (DecisionConseil decision : DecisionConseil.values()) {
            if (decision.getLibelle().equalsIgnoreCase(libelle)) {
                return decision;
            }
        }
        throw new IllegalArgumentException("Décision inconnue: " + libelle);
    }

    // Méthodes utilitaires
    public boolean estAdmission() {
        return this == ADMISSION || this == ADMISSION_SOUS_CONDITION;
    }

    public boolean estRedoublement() {
        return this == REDOUBLEMENT || this == REDOUBLEMENT_AVEC_DEROGATION;
    }

    public boolean estExclusion() {
        return this == EXCLUSION_TEMPORAIRE || this == EXCLUSION_DEFINITIVE;
    }
}
