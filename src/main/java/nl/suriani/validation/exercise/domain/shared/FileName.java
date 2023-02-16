package nl.suriani.validation.exercise.domain.shared;

public record FileName(String value) {

    public FileName {
        Guards.isRequired(value);
    }
}
