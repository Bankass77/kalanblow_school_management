package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.model.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereCoefficientModifieEvent extends DomainEvent {
    private final Long matiereId;
    private final String nom;
    private final Integer nouveauCoefficient;

    public MatiereCoefficientModifieEvent(Long matiereId, String nom, Integer nouveauCoefficient) {
        super(String.valueOf(matiereId), nom);
        this.matiereId = matiereId;
        this.nom = nom;
        this.nouveauCoefficient = nouveauCoefficient;
    }
}
