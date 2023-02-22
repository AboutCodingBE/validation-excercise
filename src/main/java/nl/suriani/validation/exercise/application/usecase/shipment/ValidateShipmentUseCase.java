package nl.suriani.validation.exercise.application.usecase.shipment;

import nl.suriani.validation.exercise.application.port.shipment.ShipmentRepository;
import nl.suriani.validation.exercise.domain.shipment.Shipment;

import static nl.suriani.validation.exercise.application.usecase.shipment.ValidateShipmentUseCaseResultCode.*;

public class ValidateShipmentUseCase {
    private final ShipmentRepository shipmentRepository;

    public ValidateShipmentUseCase(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public ValidateShipmentUseCaseResult apply(ValidateShipmentCommand command) {
        if (thereAreNoSensorIds(command)) {
            return resultIs(EMPTY_SHIPMENT);
        }

        final var shipmentReceived = Shipment.received(command.shipmentId(), command.description());

        if (thereAreDuplicatedSensorIds(command)) {
            final var shipmentNotValidated = shipmentReceived.notValidated("Duplicate sensor ids detected");
            shipmentRepository.save(shipmentNotValidated);
            return resultIs(DUPLICATE_IDS);
        }

        final var shipmentValidated = shipmentReceived.validated();
        shipmentRepository.save(shipmentValidated);
        return resultIs(OK);
    }

    private boolean thereAreNoSensorIds(ValidateShipmentCommand command) {
        return command.sensorIds().isEmpty();
    }

    private ValidateShipmentUseCaseResult resultIs(ValidateShipmentUseCaseResultCode ok) {
        return new ValidateShipmentUseCaseResult(ok);
    }

    private boolean thereAreDuplicatedSensorIds(ValidateShipmentCommand command) {
        return command.sensorIds().stream().distinct().toList().size() < command.sensorIds().size();
    }
}
