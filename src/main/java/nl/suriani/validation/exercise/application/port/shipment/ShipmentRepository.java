package nl.suriani.validation.exercise.application.port.shipment;

import nl.suriani.validation.exercise.domain.shipment.Shipment;

public interface ShipmentRepository {
    void save(Shipment shipment);
}
