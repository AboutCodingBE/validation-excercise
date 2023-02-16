package nl.suriani.validation.exercise.domain.task;

import nl.suriani.validation.exercise.domain.sensor.SensorId;
import nl.suriani.validation.exercise.domain.shared.Guards;

public record Task(SensorId sensorId, TaskId id, UpdateFile file, TaskType type, TaskStatus status) {

    public Task {
        Guards.isRequired(sensorId);
        Guards.isRequired(id);
        Guards.isRequired(file);
        Guards.isRequired(type);
        Guards.isRequired(status);
    }
}
