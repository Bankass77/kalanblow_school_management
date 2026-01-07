package com.kalanblow.school_management.model.shared.enums;

public enum GraviteSanction {
    LEGERE(1, "Légère", "Avertissement oral/écrit"),
    MOYENNE(2, "Moyenne", "Retenue, exclusion temporaire (< 3 jours)"),
    GRAVE(3, "Grave", "Exclusion temporaire (≥ 3 jours)"),
    TRES_GRAVE(4, "Très grave", "Exclusion définitive, conseil de discipline");

    private final int niveau;
    private final String libelle;
    private final String description;

    GraviteSanction(int niveau, String libelle, String description) {
        this.niveau = niveau;
        this.libelle = libelle;
        this.description = description;
    }

    public int getNiveau() { return niveau; }
    public String getLibelle() { return libelle; }
    public String getDescription() { return description; }

    public boolean estPlusGraveQue(GraviteSanction autre) {
        return this.niveau > autre.niveau;
    }

    public boolean peutConduireAExclusion() {
        return this.niveau >= GRAVE.niveau;
    }
}
