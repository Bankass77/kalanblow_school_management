package com.kalanblow.school_management.model.shared.enums;

public enum NiveauMatiere {
    MATERNELLE("Maternelle"),
    PRIMAIRE("Primaire"),
    COLLEGE("Collège"),
    LYCEE_GENERAL("Lycée général"),
    LYCEE_TECHNO("Lycée technologique"),
    LYCEE_PRO("Lycée professionnel"),
    SUPERIEUR("Supérieur");

    private final String libelle;

    NiveauMatiere(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() { return libelle; }
}
