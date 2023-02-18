package nl.suriani.validation.exercise.domain.shared;

import java.util.Collection;

public interface Guards {
    static void isRequired(Object object) {
        if (object == null) {
            throw new RequiredFieldIsMissingException();
        }
    }

    static <T> void isNotEmpty(Collection<T> collection) {
        isRequired(collection);
        if (collection.isEmpty()) {
            throw new CollectionIsEmptyException();
        }
    }
}
