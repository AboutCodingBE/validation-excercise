package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.Configuration;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateFailed extends DomainEvent {
    private final Configuration oldVersion;
    private final Configuration newVersion;
    private final String reason;

    public ConfigurationUpdateFailed(LocalDateTime creationDateTime, Configuration oldVersion, Configuration newVersion, String reason) {
        super(creationDateTime, ConfigurationUpdateFailed.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public ConfigurationUpdateFailed(Configuration oldVersion, Configuration newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
