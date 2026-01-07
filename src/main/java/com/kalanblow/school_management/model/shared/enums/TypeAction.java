package com.kalanblow.school_management.model.shared.enums;

public enum TypeAction {
    // Gestion du personnel
    EMBAUCHE_PROFESSEUR("Embauche d'un professeur", NiveauImportance.ELEVEE),
    RENOUVELLEMENT_CONTRAT("Renouvellement de contrat", NiveauImportance.MOYEN),
    SANCTION_PERSONNEL("Sanction du personnel", NiveauImportance.ELEVEE),
    PROMOTION("Promotion interne", NiveauImportance.MOYEN),

    // Gestion financière
    APPROBATION_BUDGET("Approbation du budget", NiveauImportance.CRITIQUE),
    SIGNATURE_CONTRAT("Signature de contrat", NiveauImportance.ELEVEE),
    DEPENSE_IMPORTANTE("Dépense importante", NiveauImportance.ELEVEE),

    // Gestion des élèves
    ADMISSION_ELEVE("Admission d'un élève", NiveauImportance.MOYEN),
    EXCLUSION_ELEVE("Exclusion d'un élève", NiveauImportance.ELEVEE),
    REMISE_DIPLOME("Remise de diplôme", NiveauImportance.MOYEN),

    // Infrastructure
    COMMANDE_EQUIPEMENT("Commande d'équipement", NiveauImportance.MOYEN),
    TRAVAUX_IMPORTANTS("Autorisation de travaux", NiveauImportance.ELEVEE),

    // Autres
    REUNION_DIRECTION("Réunion de direction", NiveauImportance.FAIBLE),
    DECISION_PEDAGOGIQUE("Décision pédagogique", NiveauImportance.MOYEN),
    COMMUNICATION_OFFICIELLE("Communication officielle", NiveauImportance.MOYEN);

    private final String libelle;
    private final NiveauImportance niveauImportance;

    TypeAction(String libelle, NiveauImportance niveauImportance) {
        this.libelle = libelle;
        this.niveauImportance = niveauImportance;
    }

    public String getLibelle() { return libelle; }
    public NiveauImportance getNiveauImportance() { return niveauImportance; }
}
