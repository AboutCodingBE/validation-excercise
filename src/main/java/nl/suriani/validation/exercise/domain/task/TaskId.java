package nl.suriani.validation.exercise.domain.task;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record TaskId(String value) {

    public TaskId {
        Guards.isRequired(value);
    }
}
