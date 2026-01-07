package com.kalanblow.school_management.model.shared.enums;

public enum StatutAction {
    EN_COURS("En cours de traitement"),
    REALISEE("Réalisée avec succès"),
    ANNULEE("Annulée"),
    EN_ATTENTE("En attente de validation"),
    REJETEE("Rejetée");

    private final String libelle;

    StatutAction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
