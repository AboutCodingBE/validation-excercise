package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.MalformedFieldException;
import nl.suriani.validation.exercise.domain.shared.RequiredFieldIsMissingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirmwareVersionTest {

    @Test
    void dayIsRequired() {
        assertThrows(RequiredFieldIsMissingException.class,
                () -> new FirmwareVersion(null, 1, 2020, 0));
    }

    @Test
    void monthIsRequired() {
        assertThrows(RequiredFieldIsMissingException.class,
                () -> new FirmwareVersion(1, null, 2020, 0));
    }

    @Test
    void yearIsRequired() {
        assertThrows(RequiredFieldIsMissingException.class,
                () -> new FirmwareVersion(1, 1, null, 0));
    }

    @Test
    void serialNumberIsRequired() {
        assertThrows(RequiredFieldIsMissingException.class,
                () -> new FirmwareVersion(1, 1, 2020, null));
    }

    @Test
    void incorrectDatesAreNotAllowed() {
        assertAll(
                () -> assertThrows(IncorrectFirmwareVersionException.class,
                        () -> new FirmwareVersion(0, 1, 2020, 0)),

                () -> assertThrows(IncorrectFirmwareVersionException.class,
                        () -> new FirmwareVersion(1, -1, 2020, 0)),

                () -> assertThrows(IncorrectFirmwareVersionException.class,
                        () -> new FirmwareVersion(1, 13, 2020, 0)),

                () -> assertThrows(IncorrectFirmwareVersionException.class,
                        () -> new FirmwareVersion(32, 1, 2020, 0)),

                () -> assertThrows(IncorrectFirmwareVersionException.class,
                        () -> new FirmwareVersion(30, 2, 2020, 0))
        );
    }

    @Test
    void serialNumberMustBeZeroOrPositive() {
        assertThrows(MalformedFieldException.class,
                () -> new FirmwareVersion(1, 12, 2020, -1));
    }

    @Test
    void testFromString() {
        var text = "FW:02-11-2021#03";

        var firmwareVersion = FirmwareVersion.fromString(text);

        assertEquals(2, firmwareVersion.day());
        assertEquals(11, firmwareVersion.month());
        assertEquals(2021, firmwareVersion.year());
        assertEquals(3, firmwareVersion.serialNumber());
    }

    @Test
    void testToStringWithoutZeroPadding() {
        var firmwareVersion = new FirmwareVersion(12, 11, 2021, 13);

        assertEquals("FW:12-11-2021#13", firmwareVersion.toString());
    }

    @Test
    void testToStringWithZeroPadding() {
        var firmwareVersion = new FirmwareVersion(1, 2, 3, 4);

        assertEquals("FW:01-02-0003#04", firmwareVersion.toString());
    }

}