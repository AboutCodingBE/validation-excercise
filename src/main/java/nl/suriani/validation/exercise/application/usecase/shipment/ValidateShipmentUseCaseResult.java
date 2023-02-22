package nl.suriani.validation.exercise.application.usecase.shipment;

import java.util.List;
import java.util.Objects;

public record ValidateShipmentUseCaseResult(ValidateShipmentUseCaseResultCode code, List<String> errors) {

    public ValidateShipmentUseCaseResult {
        Objects.requireNonNull(code);
        Objects.requireNonNull(errors);
    }

    public ValidateShipmentUseCaseResult(ValidateShipmentUseCaseResultCode code) {
        this(code, List.of());
    }
}
