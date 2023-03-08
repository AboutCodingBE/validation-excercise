package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateScheduled extends DomainEvent {
    private final Version oldVersion;
    private final Version newVersion;

    public ConfigurationUpdateScheduled(LocalDateTime creationDateTime, Version oldVersion, Version newVersion) {
        super(creationDateTime, ConfigurationUpdateScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public ConfigurationUpdateScheduled(Version oldVersion, Version newVersion) {
        this(LocalDateTime.now(), oldVersion, newVersion);
    }
}
