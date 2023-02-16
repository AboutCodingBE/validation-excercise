package nl.suriani.validation.exercise.domain.shared;

public interface Guards {
    static void isRequired(Object object) {
        if (object == null) {
            throw new RequiredFieldIsMissingException();
        }
    }
}
