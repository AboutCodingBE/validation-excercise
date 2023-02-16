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

}