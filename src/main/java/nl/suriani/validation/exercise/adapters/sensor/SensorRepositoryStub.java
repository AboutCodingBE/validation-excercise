package nl.suriani.validation.exercise.adapters.sensor;

import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.domain.sensor.Sensor;
import nl.suriani.validation.exercise.domain.sensor.SensorId;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SensorRepositoryStub implements SensorRepository {
    private final Map<SensorId, Sensor> db = new ConcurrentHashMap<>();

    @Override
    public Optional<Sensor> findById(SensorId sensorId) {
        return Optional.ofNullable(db.get(sensorId));
    }

    @Override
    public void saveAll(List<Sensor> sensors) {
        sensors.forEach(sensor -> db.put(sensor.id(), sensor));
    }
}
