package nl.suriani.validation.exercise.application.usecase.sensor;

import java.util.List;
import java.util.Objects;

public record CheckSensorStatusUseCaseResult(CheckSensorStatusUseCaseResultCode code, List<String> errors) {

    public CheckSensorStatusUseCaseResult {
        Objects.requireNonNull(code);
        Objects.requireNonNull(errors);
    }

    public CheckSensorStatusUseCaseResult(CheckSensorStatusUseCaseResultCode code) {
        this(code, List.of());
    }
}
