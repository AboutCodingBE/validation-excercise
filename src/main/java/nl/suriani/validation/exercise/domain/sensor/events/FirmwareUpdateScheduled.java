package nl.suriani.validation.exercise.domain.sensor.events;

import nl.suriani.validation.exercise.domain.sensor.FirmwareVersion;
import nl.suriani.validation.exercise.domain.shared.DomainEvent;
import nl.suriani.validation.exercise.domain.shared.Guards;

import java.time.LocalDateTime;

public class FirmwareUpdateScheduled extends DomainEvent {
    private final FirmwareVersion oldVersion;
    private final FirmwareVersion newVersion;
    private final String reason;

    public FirmwareUpdateScheduled(LocalDateTime creationDateTime, FirmwareVersion oldVersion, FirmwareVersion newVersion, String reason) {
        super(creationDateTime, FirmwareUpdateScheduled.class);

        Guards.isRequired(oldVersion);
        Guards.isRequired(newVersion);
        Guards.isRequired(reason);

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;
        this.reason = reason;
    }

    public FirmwareUpdateScheduled(FirmwareVersion oldVersion, FirmwareVersion newVersion, String reason) {
        this(LocalDateTime.now(), oldVersion, newVersion, reason);
    }
}
