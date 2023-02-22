package nl.suriani.validation.exercise.application.usecase.shipment;

import nl.suriani.validation.exercise.domain.sensor.SensorId;
import nl.suriani.validation.exercise.domain.shipment.ShipmentDescription;
import nl.suriani.validation.exercise.domain.shipment.ShipmentId;

import java.util.List;
import java.util.Objects;

public record ValidateShipmentCommand(ShipmentId shipmentId, ShipmentDescription description, List<SensorId> sensorIds) {

    public ValidateShipmentCommand {
        Objects.requireNonNull(shipmentId);
        Objects.requireNonNull(description);
        Objects.requireNonNull(sensorIds);
    }
}
