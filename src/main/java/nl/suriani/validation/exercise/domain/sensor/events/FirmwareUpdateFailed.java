package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.FirmwareVersion;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;
import java.util.Optional;

public class FirmwareUpdateFailed extends DomainEvent {
    private final Version oldVersion;
    private final Version newVersion;
    private final String reason;

    public FirmwareUpdateFailed(LocalDateTime creationDateTime, Version oldVersion, Version newVersion, String reason) {
        super(creationDateTime, FirmwareUpdateFailed.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public FirmwareUpdateFailed(Version oldVersion, Version newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
