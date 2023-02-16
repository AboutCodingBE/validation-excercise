package nl.suriani.validation.exercise.domain.shared;

public class MalformedFieldException extends DomainValidationException {
    public MalformedFieldException(String message) {
        super(message);
    }
}
