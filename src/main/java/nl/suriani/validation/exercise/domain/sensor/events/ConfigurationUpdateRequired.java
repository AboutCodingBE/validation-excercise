package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;

import java.time.LocalDateTime;

public class ConfigurationUpdateRequired extends DomainEvent {

    public ConfigurationUpdateRequired() {
        super(LocalDateTime.now(), ConfigurationUpdateRequired.class);
    }
}
