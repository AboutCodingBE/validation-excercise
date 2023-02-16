package nl.suriani.validation.exercise.domain.shared;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException() {
    }

    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(Throwable cause) {
        super(cause);
    }
}
