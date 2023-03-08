package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.Configuration;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateFailed extends DomainEvent {
    private final Version oldVersion;
    private final Version newVersion;
    private final String reason;

    public ConfigurationUpdateFailed(LocalDateTime creationDateTime, Version oldVersion, Version newVersion, String reason) {
        super(creationDateTime, ConfigurationUpdateFailed.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public ConfigurationUpdateFailed(Version oldVersion, Version newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
