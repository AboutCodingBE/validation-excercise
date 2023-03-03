package nl.suriani.validation.exercise.application.usecase.task;

import nl.suriani.validation.exercise.domain.sensor.SensorId;
import nl.suriani.validation.exercise.domain.task.TaskType;

import java.util.Objects;

public record ScheduleTaskCommand(SensorId sensorId, TaskType taskType) {

    public ScheduleTaskCommand {
        Objects.requireNonNull(sensorId);
        Objects.requireNonNull(taskType);
    }
}
