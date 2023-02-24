package nl.suriani.validation.exercise.application.port.sensor;

import nl.suriani.validation.exercise.domain.sensor.*;

import java.util.Objects;

public record SensorAtManufacturer(SensorId id,
                                   Firmware firmware,
                                   Configuration configuration,
                                   SensorTasksStatus tasksStatus,
                                   SensorActivityStatus activityStatus) {

    public SensorAtManufacturer {
        Objects.requireNonNull(id);
        Objects.requireNonNull(firmware);
        Objects.requireNonNull(configuration);
        Objects.requireNonNull(tasksStatus);
        Objects.requireNonNull(activityStatus);
    }
}
