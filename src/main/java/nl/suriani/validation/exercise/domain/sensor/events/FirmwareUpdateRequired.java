package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public class FirmwareUpdateRequired extends DomainEvent {

    public FirmwareUpdateRequired(LocalDateTime creationDateTime) {
        super(creationDateTime, FirmwareUpdateRequired.class);
    }
}
