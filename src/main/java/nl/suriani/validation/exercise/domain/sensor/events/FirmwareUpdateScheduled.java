package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class FirmwareUpdateScheduled extends DomainEvent {
    private final Version oldVersion;
    private final Version newVersion;

    public FirmwareUpdateScheduled(LocalDateTime creationDateTime, Version oldVersion, Version newVersion) {
        super(creationDateTime, FirmwareUpdateScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public FirmwareUpdateScheduled(Version oldVersion, Version newVersion) {
        this(LocalDateTime.now(), oldVersion, newVersion);
    }
}
