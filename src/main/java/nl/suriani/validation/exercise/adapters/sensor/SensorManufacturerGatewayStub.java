package nl.suriani.validation.exercise.adapters.sensor;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.domain.sensor.*;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.task.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SensorManufacturerGatewayStub implements SensorManufacturerGateway {
    private final Map<SensorId, SensorAtManufacturer> sensorDb = new ConcurrentHashMap<>();
    private final Map<SensorId, Task> taskDb = new ConcurrentHashMap<>();

    @Override
    public Optional<SensorAtManufacturer> findSensorById(SensorId sensorId) {
        if (sensorId.value().contains("unknown")) {
            return Optional.empty();
        }

        var sensor = new SensorAtManufacturer(sensorId,
                new Firmware(new FirmwareVersion(1, 1, 2020, 1)),
                new Configuration(new FileName("someOldCfg.cfg")),
                SensorTasksStatus.IDLE,
                SensorActivityStatus.ONLINE);

        sensorDb.put(sensorId, sensor);

        return Optional.of(sensor);
    }

    @Override
    public Optional<Task> findTaskBySensorId(SensorId sensorId) {
        return Optional.ofNullable(taskDb.get(sensorId));
    }

    @Override
    public Task scheduleConfigurationUpdate(SensorId sensorId) {
        var task = new Task(sensorId,
                new TaskId(),
                new UpdateFile(new FileName("someCfg.cfg")),
                TaskType.UPDATE_CONFIGURATION,
                TaskStatus.PENDING);

        taskDb.put(sensorId, task);
        return task;
    }

    @Override
    public Task scheduleFirmwareUpdate(SensorId sensorId) {
        var task = new Task(sensorId,
                new TaskId(),
                new UpdateFile(new FileName("someFw.dat")),
                TaskType.UPDATE_FIRMWARE,
                TaskStatus.PENDING);

        taskDb.put(sensorId, task);
        return task;
    }

    @Override
    public boolean needsFirmwareUpdate(SensorAtManufacturer sensor) {
        var firstCompatibleFirmwareVersion = new FirmwareVersion(23, 9, 2022, 3);
        return sensor.firmware().version().isBefore(firstCompatibleFirmwareVersion);
    }

    @Override
    public boolean needsConfigurationUpdate(SensorAtManufacturer sensor) {
        return true;
    }

    @Override
    public void cancelTask(TaskId taskId) {
        taskDb.remove(taskId);
    }

    @Override
    public Optional<FileName> getLastFirmwareInformation() {
        return Optional.of(new FileName("VeryNewFirmware.dat"));
    }

    @Override
    public Optional<FileName> getLastConfigurationInformation() {
        return Optional.of(new FileName("VeryNewConfiguration.cfg"));
    }
}
