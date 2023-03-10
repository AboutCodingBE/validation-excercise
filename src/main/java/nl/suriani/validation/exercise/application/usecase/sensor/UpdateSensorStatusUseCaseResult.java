package nl.suriani.validation.exercise.application.usecase.sensor;

import java.util.List;
import java.util.Objects;

public record UpdateSensorStatusUseCaseResult(UpdateSensorStatusUseCaseResultCode code, List<String> errors) {

    public UpdateSensorStatusUseCaseResult {
        Objects.requireNonNull(code);
        Objects.requireNonNull(errors);
    }

    public UpdateSensorStatusUseCaseResult(UpdateSensorStatusUseCaseResultCode code) {
        this(code, List.of());
    }
}
