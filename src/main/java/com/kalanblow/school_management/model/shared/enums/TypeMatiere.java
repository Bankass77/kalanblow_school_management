package com.kalanblow.school_management.model.shared.enums;

public enum TypeMatiere {
    FONDAMENTALE("Matière fondamentale", 4.0),
    OBLIGATOIRE("Matière obligatoire", 3.0),
    OPTIONNELLE("Matière optionnelle", 2.0),
    COMPLEMENTAIRE("Matière complémentaire", 1.0),
    PROJET("Projet / TPE", 2.0),
    SPORT("Éducation physique et sportive", 1.0),
    ARTISTIQUE("Éducation artistique", 1.0);

    private final String libelle;
    private final double coefficientBase;

    TypeMatiere(String libelle, double coefficientBase) {
        this.libelle = libelle;
        this.coefficientBase = coefficientBase;
    }

    public String getLibelle() { return libelle; }
    public double getCoefficientBase() { return coefficientBase; }

    public boolean estCoefficientFixe() {
        return this == FONDAMENTALE || this == OBLIGATOIRE;
    }
}