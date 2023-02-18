package nl.suriani.validation.exercise.domain.shipment.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public final class ShipmentReceived extends DomainEvent {
    public ShipmentReceived(LocalDateTime creationDateTime) {
        super(creationDateTime, ShipmentReceived.class);
    }

    public ShipmentReceived() {
        this(LocalDateTime.now());
    }
}
