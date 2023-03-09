package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateScheduled extends DomainEvent {
    private final Version version;
    private final FileName fileName;

    public ConfigurationUpdateScheduled(LocalDateTime creationDateTime, Version version, FileName fileName) {
        super(creationDateTime, ConfigurationUpdateScheduled.class);

        Guards.isRequired(version);
        Guards.isRequired(fileName);

        this.version = version;
        this.fileName = fileName;
    }

    public ConfigurationUpdateScheduled(Version version, FileName fileName) {
        this(LocalDateTime.now(), version, fileName);
    }
}
