package com.kalanblow.school_management.model.shared.enums;

public enum StatutProfesseur {
    TITULAIRE("Titulaire"),
    CONTRACTUEL("Contractuel"),
    VACATAIRE("Vacataire"),
    STAGIAIRE("Stagiaire");

    private final String libelle;

    StatutProfesseur(String libelle) {
        this.libelle = libelle;
    }
}
