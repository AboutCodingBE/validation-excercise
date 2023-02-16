package nl.suriani.validation.exercise.domain.task;

import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.shared.Guards;

public record UpdateFile(FileName name) {

    public UpdateFile {
        Guards.isRequired(name);
    }
}
