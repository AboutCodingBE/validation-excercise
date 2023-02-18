package nl.suriani.validation.exercise.domain.shipment.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public final class ShipmentValidationFailed extends DomainEvent {

    private final String reason;

    public ShipmentValidationFailed(String reason, LocalDateTime creationDateTime) {
        super(creationDateTime, ShipmentValidationFailed.class);
        this.reason = reason;
    }

    public ShipmentValidationFailed(String reason) {
        this(reason, LocalDateTime.now());
    }

    public String reason() {
        return reason;
    }
}
