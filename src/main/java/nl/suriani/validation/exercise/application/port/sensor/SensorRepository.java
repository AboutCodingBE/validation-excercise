package nl.suriani.validation.exercise.application.port.sensor;

import nl.suriani.validation.exercise.domain.sensor.Sensor;
import nl.suriani.validation.exercise.domain.sensor.SensorId;

import java.util.List;
import java.util.Optional;

public interface SensorRepository {
    Optional<Sensor> findById(SensorId sensorId);
    void saveAll(List<Sensor> sensors);
    default void save(Sensor sensor) {
        saveAll(List.of(sensor));
    }
}
