package nl.suriani.validation.exercise.application.usecase.sensor;

public enum UpdateSensorStatusUseCaseResultCode {
    NOT_REGISTERED,
    NO_ACTION_NEEDED,
    CONFIGURATION_UPDATE_REQUIRED,
    FIRMWARE_UPDATE_REQUIRED,
    ERROR
}
