package nl.suriani.validation.exercise.domain.shipment.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public final class ShipmentNotValidated extends DomainEvent {

    private final String reason;

    public ShipmentNotValidated(String reason, LocalDateTime creationDateTime) {
        super(creationDateTime, ShipmentNotValidated.class);
        this.reason = reason;
    }

    public ShipmentNotValidated(String reason) {
        this(reason, LocalDateTime.now());
    }

    public String reason() {
        return reason;
    }
}
