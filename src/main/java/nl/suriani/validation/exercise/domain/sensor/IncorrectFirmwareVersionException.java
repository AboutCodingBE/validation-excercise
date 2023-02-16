package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.DomainValidationException;

public class IncorrectFirmwareVersionException extends DomainValidationException {
    public IncorrectFirmwareVersionException(Throwable cause) {
        super(cause);
    }
}
