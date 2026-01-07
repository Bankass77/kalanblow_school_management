package com.kalanblow.school_management.model.shared.enums;

public enum NiveauImportance {
    FAIBLE("Faible", 1),
    MOYEN("Moyen", 2),
    ELEVEE("Élevée", 3),
    CRITIQUE("Critique", 4);

    private final String libelle;
    private final int valeur;

    NiveauImportance(String libelle, int valeur) {
        this.libelle = libelle;
        this.valeur = valeur;
    }

    public int getValeur() { return valeur; }
    public String getLibelle() { return libelle; }
}
