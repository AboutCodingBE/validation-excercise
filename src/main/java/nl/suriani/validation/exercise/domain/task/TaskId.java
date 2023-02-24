package nl.suriani.validation.exercise.domain.task;

import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shared.HexStringGenerator;

public record TaskId(String value) {

    public TaskId {
        Guards.isRequired(value);
    }

    public TaskId() {
        this(HexStringGenerator.generate());
    }
}
