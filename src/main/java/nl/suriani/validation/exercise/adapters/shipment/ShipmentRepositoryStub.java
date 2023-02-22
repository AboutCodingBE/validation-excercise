package nl.suriani.validation.exercise.adapters.shipment;

import nl.suriani.validation.exercise.application.port.shipment.ShipmentRepository;
import nl.suriani.validation.exercise.domain.shipment.Shipment;
import nl.suriani.validation.exercise.domain.shipment.ShipmentId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShipmentRepositoryStub implements ShipmentRepository {

    private final Map<ShipmentId, Shipment> db = new ConcurrentHashMap<>();

    @Override
    public void save(Shipment shipment) {
        db.put(shipment.id(), shipment);
    }
}
