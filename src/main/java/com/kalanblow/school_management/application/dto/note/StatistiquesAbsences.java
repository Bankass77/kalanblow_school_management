package com.kalanblow.school_management.application.dto.note;

public record StatistiquesAbsences( Long studentId,
                                    long totalAbsences,
                                    long absencesJustifiees,
                                    long absencesNonJustifiees) {

    // Calcul des taux avec précision
    public double tauxAbsentisme() {
        return totalAbsences > 0 ?
                (absencesNonJustifiees * 100.0) / totalAbsences : 0.0;
    }

    public double tauxJustification() {
        return totalAbsences > 0 ?
                (absencesJustifiees * 100.0) / totalAbsences : 0.0;
    }

    public boolean aTropDAbsencesNonJustifiees() {
        return tauxAbsentisme() > 20.0; // Plus de 20% d'absences non justifiées
    }

    public String getResume() {
        return String.format(
                "Total: %d | Justifiées: %d (%.1f%%) | Non justifiées: %d (%.1f%%)",
                totalAbsences, absencesJustifiees, tauxJustification(),
                absencesNonJustifiees, tauxAbsentisme()
        );
    }
}
