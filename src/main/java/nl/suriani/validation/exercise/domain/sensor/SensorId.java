package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record SensorId(String value) {

    public SensorId {
        Guards.isRequired(value);
    }
}
