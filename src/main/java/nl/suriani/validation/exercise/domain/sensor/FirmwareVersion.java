package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;
import nl.suriani.validation.exercise.domain.shared.MalformedFieldException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public record FirmwareVersion(Integer day, Integer month, Integer year, Integer serialNumber) {

    public FirmwareVersion {
        Guards.isRequired(day);
        Guards.isRequired(month);
        Guards.isRequired(year);
        Guards.isRequired(serialNumber);

        checkDate(day, month, year);
        checkSerialNumber(serialNumber);
    }

    public static FirmwareVersion fromString(String text) {
        var pattern = Pattern.compile("FW:([0-9]{2})-([0-9]{2})-([0-9]{4})#([0-9]{2})");
        var matcher = pattern.matcher(text);

        if (!matcher.matches()) {
            throw new MalformedFieldException("Invalid firmware version '" + text + "'");
        }

        var day = Integer.parseInt(matcher.group(1));
        var month = Integer.parseInt(matcher.group(2));
        var year = Integer.parseInt(matcher.group(3));
        var serialNumber = Integer.parseInt(matcher.group(4));
        return new FirmwareVersion(day, month, year, serialNumber);
    }

    public boolean isSameAs(FirmwareVersion otherVersion) {
        return this.equals(otherVersion);
    }

    public boolean isAfter(FirmwareVersion otherVersion) {
        var thisFirmwareDate = LocalDate.of(year,month, day);
        var otherFirmwareDate = LocalDate.of(otherVersion.year, otherVersion.month, otherVersion.day);

        if (thisFirmwareDate.isBefore(otherFirmwareDate)) {
            return false;
        }

        if (thisFirmwareDate.isAfter(otherFirmwareDate)) {
            return true;
        }

        return serialNumber > otherVersion.serialNumber;
    }

    public boolean isBefore(FirmwareVersion otherVersion) {
        return !isSameAs(otherVersion) && !isAfter(otherVersion);
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

    @Override
    public String toString() {
        return String.format("FW:%02d-%02d-%04d#%02d", day, month, year, serialNumber);
    }
}
