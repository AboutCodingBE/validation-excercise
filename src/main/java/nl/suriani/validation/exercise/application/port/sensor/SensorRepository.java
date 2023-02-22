package nl.suriani.validation.exercise.application.port.sensor;

import nl.suriani.validation.exercise.domain.sensor.Sensor;

import java.util.List;

public interface SensorRepository {
    void saveAll(List<Sensor> sensors);
}
