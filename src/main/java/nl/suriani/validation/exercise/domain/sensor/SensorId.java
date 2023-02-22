package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shared.HexStringGenerator;

public record SensorId(String value) {

    public SensorId {
        Guards.isRequired(value);
    }

    public SensorId() {
        this(HexStringGenerator.generate());
    }
}
