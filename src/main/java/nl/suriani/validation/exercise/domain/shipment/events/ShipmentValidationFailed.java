package nl.suriani.validation.exercise.domain.shipment.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public final class ShipmentValidationFailed extends DomainEvent {

    private final String reason;

    public ShipmentValidationFailed(String reason, LocalDateTime creationDateTime) {
        super(creationDateTime, ShipmentValidationFailed.class);

        Guards.isRequired(reason);
        this.reason = reason;
    }

    public ShipmentValidationFailed(String reason) {
        this(reason, LocalDateTime.now());
    }

    public String reason() {
        return reason;
    }
}
