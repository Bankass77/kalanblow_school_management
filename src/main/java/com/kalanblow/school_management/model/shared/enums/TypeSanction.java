package com.kalanblow.school_management.model.shared.enums;

public enum TypeSanction {
    AVERTISSEMENT_ORAL("Avertissement oral"),
    AVERTISSEMENT_ECRIT("Avertissement écrit"),
    RETENUE("Retenue"),
    EXCLUSION_TEMPORAIRE("Exclusion temporaire"),
    EXCLUSION_DEFINITIVE("Exclusion définitive"),
    TRAVAIL_D_INTERET_GENERAL("Travail d'intérêt général"),
    CONSEIL_DE_DISCIPLINE("Conseil de discipline"),
    AUTRE("Autre");

    private final String libelle;

    TypeSanction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() { return libelle; }
}
