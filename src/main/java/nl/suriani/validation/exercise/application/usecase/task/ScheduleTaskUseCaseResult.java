package nl.suriani.validation.exercise.application.usecase.task;

import java.util.Objects;
import java.util.Optional;

public record ScheduleTaskUseCaseResult(ScheduleTaskUseCaseResultCode code, Optional<String> error) {

    public ScheduleTaskUseCaseResult {
        Objects.requireNonNull(code);
        Objects.requireNonNull(error);

        if (code == ScheduleTaskUseCaseResultCode.ERROR && error.isEmpty()) {
            throw new IllegalStateException("Expected information about error occurred");
        }
    }
}
