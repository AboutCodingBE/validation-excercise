package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateNotScheduled extends DomainEvent {
    private final Version oldVersion;
    private final String reason;

    public ConfigurationUpdateNotScheduled(LocalDateTime creationDateTime, Version oldVersion, String reason) {
        super(creationDateTime, ConfigurationUpdateNotScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.reason = reason;
    }

    public ConfigurationUpdateNotScheduled(Version oldVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, reason);
    }
}
