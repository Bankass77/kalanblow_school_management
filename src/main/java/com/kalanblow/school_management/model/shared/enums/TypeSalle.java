package com.kalanblow.school_management.model.shared.enums;

public enum TypeSalle {
    GENERALE("Salle générale"),
    LABORATOIRE_SCIENCES("Laboratoire de sciences"),
    SALLE_INFORMATIQUE("Salle informatique"),
    ATELIER("Atelier"),
    BIBLIOTHEQUE("Bibliothèque"),
    SALLE_ART("Salle d'art"),
    SALLE_MUSIQUE("Salle de musique"),
    GYMNASE("Gymnase"),
    EXTERIEUR("Espace extérieur");

    private final String libelle;

    TypeSalle(String libelle) {
        this.libelle = libelle;
    }
}
