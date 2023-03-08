package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;
import java.util.Optional;

public class ConfigurationUpdateNotScheduled extends DomainEvent {
    private final Version oldVersion;
    private final Optional<Version> newVersion;
    private final String reason;

    public ConfigurationUpdateNotScheduled(LocalDateTime creationDateTime, Version oldVersion, Optional<Version> newVersion, String reason) {
        super(creationDateTime, ConfigurationUpdateNotScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public ConfigurationUpdateNotScheduled(Version oldVersion, Optional<Version> newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
