package nl.suriani.validation.exercise.domain.shipment;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record ShipmentDescription(String value) {

    public ShipmentDescription {
        Guards.isRequired(value);
    }
}
