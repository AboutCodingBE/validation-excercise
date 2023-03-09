package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class ConfigurationUpdateFailed extends DomainEvent {
    private final Version version;
    private final FileName fileName;
    private final String reason;

    public ConfigurationUpdateFailed(LocalDateTime creationDateTime, Version version, FileName fileName, String reason) {
        super(creationDateTime, ConfigurationUpdateFailed.class);

        Guards.isRequired(version);
        Guards.isRequired(fileName);
        Guards.isRequired(reason);

        this.version = version;
        this.fileName = fileName;
        this.reason = reason;
    }

    public ConfigurationUpdateFailed(Version version, FileName fileName, String reason) {
        this(LocalDateTime.now(), version, fileName, reason);
    }
}
