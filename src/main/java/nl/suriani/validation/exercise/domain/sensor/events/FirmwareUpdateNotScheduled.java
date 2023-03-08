package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class FirmwareUpdateNotScheduled extends DomainEvent {
    private final Version oldVersion;
    private final String reason;

    public FirmwareUpdateNotScheduled(LocalDateTime creationDateTime, Version oldVersion, String reason) {
        super(creationDateTime, FirmwareUpdateNotScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.reason = reason;
    }

    public FirmwareUpdateNotScheduled(Version oldVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, reason);
    }
}
