package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shared.MalformedFieldException;

import java.time.DateTimeException;
import java.time.LocalDate;

public record FirmwareVersion(Integer day, Integer month, Integer year, Integer serialNumber) {

    public FirmwareVersion { // TODO implement toString and fromString
        Guards.isRequired(day);
        Guards.isRequired(month);
        Guards.isRequired(year);
        Guards.isRequired(serialNumber);

        checkDate(day, month, year);
        checkSerialNumber(serialNumber);
    }

    private void checkDate(Integer day, Integer month, Integer year) {
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException dateTimeException) {
            throw new IncorrectFirmwareVersionException(dateTimeException);
        }
    }

    private void checkSerialNumber(Integer serialNumber) {
        if (serialNumber < 0) {
            throw new MalformedFieldException("serialNumber must be 0 or positive");
        }
    }
}
