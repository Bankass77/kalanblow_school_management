package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.model.event.DomainEvent;
import com.kalanblow.school_management.model.shared.enums.TypeMatiere;

public class MatiereCreeeEvent extends DomainEvent {
    public MatiereCreeeEvent(Long matiereId, String nom, TypeMatiere type) {
        super(String.valueOf(matiereId), nom);
    }
}
