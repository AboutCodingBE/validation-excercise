package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.Configuration;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateScheduled extends DomainEvent {
    private final Configuration oldVersion;
    private final Configuration newVersion;

    public ConfigurationUpdateScheduled(LocalDateTime creationDateTime, Configuration oldVersion, Configuration newVersion) {
        super(creationDateTime, ConfigurationUpdateScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public ConfigurationUpdateScheduled(Configuration oldVersion, Configuration newVersion) {
        this(LocalDateTime.now(), oldVersion, newVersion);
    }
}
