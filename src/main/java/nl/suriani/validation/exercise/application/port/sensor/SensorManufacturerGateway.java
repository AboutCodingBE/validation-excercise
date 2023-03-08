package nl.suriani.validation.exercise.application.port.sensor;

import nl.suriani.validation.exercise.domain.sensor.*;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.task.Task;
import nl.suriani.validation.exercise.domain.task.TaskId;

import java.util.Optional;

public interface SensorManufacturerGateway {
    Optional<SensorAtManufacturer> findSensorById(SensorId sensorId);
    Optional<Task> findTaskBySensorId(SensorId sensorId);
    Task scheduleConfigurationUpdate(SensorId sensorId, FileName fileName);
    Task scheduleFirmwareUpdate(SensorId sensorId, FileName fileName);
    boolean needsFirmwareUpdate(SensorAtManufacturer sensor);
    boolean needsConfigurationUpdate(SensorAtManufacturer sensor);
    void cancelTask(TaskId taskId);
    Optional<FileName> getLastFirmwareInformation();
    Optional<FileName> getLastConfigurationInformation();
}
