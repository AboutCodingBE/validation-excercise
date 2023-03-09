package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateNotScheduled extends DomainEvent {
    private final Version version;
    private final String reason;

    public ConfigurationUpdateNotScheduled(LocalDateTime creationDateTime, Version version, String reason) {
        super(creationDateTime, ConfigurationUpdateNotScheduled.class);

        Guards.isRequired(version);
        Guards.isRequired(reason);

        this.version = version;
        this.reason = reason;
    }

    public ConfigurationUpdateNotScheduled(Version version, String reason) {
        this(LocalDateTime.now(), version, reason);
    }
}
