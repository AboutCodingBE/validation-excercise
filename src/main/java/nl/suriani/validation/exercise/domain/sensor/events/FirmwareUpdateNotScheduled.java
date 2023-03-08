package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;
import java.util.Optional;

public class FirmwareUpdateNotScheduled extends DomainEvent {
    private final Version oldVersion;
    private final Optional<Version> newVersion;
    private final String reason;

    public FirmwareUpdateNotScheduled(LocalDateTime creationDateTime, Version oldVersion, Optional<Version> newVersion, String reason) {
        super(creationDateTime, FirmwareUpdateNotScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public FirmwareUpdateNotScheduled(Version oldVersion, Optional<Version> newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
