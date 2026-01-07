package com.kalanblow.school_management.model.matiere;

import com.kalanblow.school_management.model.event.DomainEvent;

public class MatiereReactiveeEvent extends DomainEvent {
    protected MatiereReactiveeEvent(String aggregateId, String aggregateType) {
        super(aggregateId, aggregateType);
    }
}
