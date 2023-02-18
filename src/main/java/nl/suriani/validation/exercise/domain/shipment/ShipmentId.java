package nl.suriani.validation.exercise.domain.shipment;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record ShipmentId(String value) {

    public ShipmentId {
        Guards.isRequired(value);
    }
}
