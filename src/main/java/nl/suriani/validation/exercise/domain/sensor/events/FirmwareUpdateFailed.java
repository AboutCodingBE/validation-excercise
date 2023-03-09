package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class FirmwareUpdateFailed extends DomainEvent {
    private final Version version;
    private final FileName fileName;
    private final String reason;

    public FirmwareUpdateFailed(LocalDateTime creationDateTime, Version version, FileName fileName, String reason) {
        super(creationDateTime, FirmwareUpdateFailed.class);

        Guards.isRequired(version);
        Guards.isRequired(fileName);
        Guards.isRequired(reason);

        this.version = version;
        this.fileName = fileName;
        this.reason = reason;
    }

    public FirmwareUpdateFailed(Version version, FileName fileName, String reason) {
        this(LocalDateTime.now(), version, fileName, reason);
    }
}
