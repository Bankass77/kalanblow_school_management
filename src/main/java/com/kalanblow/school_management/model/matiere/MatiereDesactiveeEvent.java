package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.model.event.DomainEvent;

public class MatiereDesactiveeEvent extends DomainEvent {
    protected MatiereDesactiveeEvent(String aggregateId, String aggregateType) {
        super(aggregateId, aggregateType);
    }
}
