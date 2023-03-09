package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.FileName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {

    @Test
    void sensorRegistered() {
        assertDoesNotThrow(this::givenRegisteredSensor);
    }

    @Test
    void sensorRegisteredThenFirmwareUpdated() {
        assertDoesNotThrow(() -> givenRegisteredSensor()
                .firmwareUpdated(new FileName("bla")));
    }

    // TODO continue

    private Sensor givenRegisteredSensor() {
        return Sensor.registered(new SensorId("abc"),
                new Firmware(new FirmwareVersion(12, 1, 2021, 1)),
                new Configuration(new FileName("blablabla")),
                SensorTasksStatus.IDLE,
                SensorActivityStatus.ONLINE);
    }
}