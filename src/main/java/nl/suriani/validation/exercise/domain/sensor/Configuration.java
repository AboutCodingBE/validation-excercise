package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.shared.Guards;

public record Configuration(FileName name) {

    public Configuration {
        Guards.isRequired(name);
    }
}
