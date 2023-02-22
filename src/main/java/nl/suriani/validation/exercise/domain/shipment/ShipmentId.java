package nl.suriani.validation.exercise.domain.shipment;

import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shared.HexStringGenerator;

public record ShipmentId(String value) {

    public ShipmentId {
        Guards.isRequired(value);
    }

    public ShipmentId() {
        this(HexStringGenerator.generate());
    }
}
