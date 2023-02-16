package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record Sensor(SensorId id,
                     Firmware firmware,
                     Configuration configuration,
                     SensorTasksStatus tasksStatus,
                     SensorActivityStatus activityStatus) {

    public Sensor {
        Guards.isRequired(id);
        Guards.isRequired(firmware);
        Guards.isRequired(configuration);
        Guards.isRequired(tasksStatus);
        Guards.isRequired(activityStatus);
    }
}
