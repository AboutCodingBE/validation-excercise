package nl.suriani.validation.exercise.application.usecase.task;

public enum ScheduleTaskUseCaseResultCode {
    TASK_SCHEDULED,
    SENSOR_NOT_FOUND,
    SENSOR_NOT_KNOWN_BY_MANUFACTURER,
    SENSOR_IS_ALREADY_UPDATING,
    ERROR
}
