package nl.suriani.validation.exercise.domain.shipment.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public final class ShipmentValidated extends DomainEvent {
    public ShipmentValidated(LocalDateTime creationDateTime) {
        super(creationDateTime, ShipmentValidated.class);
    }

    public ShipmentValidated() {
        this(LocalDateTime.now());
    }
}
