package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.FirmwareVersion;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class FirmwareUpdateCancelled extends DomainEvent {
    private final FirmwareVersion oldVersion;
    private final FirmwareVersion newVersion;

    public FirmwareUpdateCancelled(LocalDateTime creationDateTime, FirmwareVersion oldVersion, FirmwareVersion newVersion) {
        super(creationDateTime, FirmwareUpdateCancelled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
    }

    public FirmwareUpdateCancelled(FirmwareVersion oldVersion, FirmwareVersion newVersion) {
        this(LocalDateTime.now(), oldVersion, newVersion);
    }
}
