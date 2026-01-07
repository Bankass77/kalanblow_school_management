package com.kalanblow.school_management.model.shared.enums;

public enum TypeEvenement {
    CONSEIL_DE_CLASSE("Conseil de classe"),
    REUNION_PARENTS("Réunion parents-professeurs"),
    EXAMEN("Examen"),
    COMPOSITION("Composition"),
    FETE_SCOLAIRE("Fête scolaire"),
    SORTIE_PEDAGOGIQUE("Sortie pédagogique"),
    STAGE("Période de stage"),
    FORMATION("Formation"),
    VACANCES("Vacances"),
    JOUR_FERIE("Jour férié"),
    AUTRE("Autre événement");

    private final String libelle;

    TypeEvenement(String libelle) {
        this.libelle = libelle;
    }
}
