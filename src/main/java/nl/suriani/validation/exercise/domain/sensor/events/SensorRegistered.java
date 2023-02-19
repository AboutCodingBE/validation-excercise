package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public class SensorRegistered extends DomainEvent {

    public SensorRegistered(LocalDateTime creationDateTime) {
        super(creationDateTime, SensorRegistered.class);
    }

    public SensorRegistered() {
        this(LocalDateTime.now());
    }
}
