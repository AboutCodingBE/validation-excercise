package nl.suriani.validation.exercise.application.usecase.task;

import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.task.TaskRepository;
import nl.suriani.validation.exercise.domain.sensor.Sensor;
import nl.suriani.validation.exercise.domain.sensor.SensorTasksStatus;
import nl.suriani.validation.exercise.domain.shared.FileName;
import nl.suriani.validation.exercise.domain.task.TaskType;

import java.util.Optional;

public class ScheduleTaskUseCase {
    private final SensorRepository sensorRepository;
    private final TaskRepository taskRepository;
    private final SensorManufacturerGateway sensorManufacturerGateway;

    public ScheduleTaskUseCase(SensorRepository sensorRepository, TaskRepository taskRepository, SensorManufacturerGateway sensorManufacturerGateway) {
        this.sensorRepository = sensorRepository;
        this.taskRepository = taskRepository;
        this.sensorManufacturerGateway = sensorManufacturerGateway;
    }

    public ScheduleTaskUseCaseResult apply(ScheduleTaskCommand command) {
        var maybeSensor = sensorRepository.findById(command.sensorId());
        if (maybeSensor.isEmpty()) {
            return answer(ScheduleTaskUseCaseResultCode.SENSOR_NOT_FOUND);
        }

        var sensor = maybeSensor.get();

        var maybeSensorAtManufacturer = sensorManufacturerGateway.findSensorById(command.sensorId());
        if (maybeSensorAtManufacturer.isEmpty()) {
            saveSensorWithNotScheduledEvent(new SaveSensorCommand(sensor, command.taskType()),
                    "Sensor is not known by manufacturer");
            return answer(ScheduleTaskUseCaseResultCode.SENSOR_NOT_KNOWN_BY_MANUFACTURER);
        }

        var sensorAtManufacturer = maybeSensorAtManufacturer.get();

        if (sensorAtManufacturer.tasksStatus() == SensorTasksStatus.UPDATING) {
            saveSensorWithNotScheduledEvent(new SaveSensorCommand(sensor, command.taskType()),
                    "Sensor is already updating");
            return answer(ScheduleTaskUseCaseResultCode.SENSOR_IS_ALREADY_UPDATING);
        }

        var maybeFileName = getUpdateInformation(command);
        if (maybeFileName.isEmpty()) {
            var error = command.taskType() + " task not scheduled: update information not available";
            saveSensorWithNotScheduledEvent(new SaveSensorCommand(sensor, command.taskType()), error);
            return answerError(error);
        }

        var fileName = maybeFileName.get();
        try {
            scheduleTask(command, fileName);
        } catch (Exception exception) {

        }

        var error = command.taskType() + " task not scheduled: update information not available";
        saveSensorWithNotScheduledEvent(new SaveSensorCommand(sensor, command.taskType()), error);
        return answerError(error);
    }

    private void saveSensorWithNotScheduledEvent(SaveSensorCommand command, String reason) {
        var sensor = command.sensor;
        switch (command.taskType()) {
            case UPDATE_FIRMWARE -> sensorRepository.save(sensor.firmwareUpdateNotScheduled(reason));
            case UPDATE_CONFIGURATION -> sensorRepository.save(sensor.configurationUpdateNotScheduled(reason));
            default -> throw new IllegalStateException();
        }
    }

    private Optional<FileName> getUpdateInformation(ScheduleTaskCommand command) {
        return switch (command.taskType()) {
            case UPDATE_FIRMWARE -> sensorManufacturerGateway.getLastFirmwareInformation();
            case UPDATE_CONFIGURATION -> sensorManufacturerGateway.getLastConfigurationInformation();
        };
    }

    private void scheduleTask(ScheduleTaskCommand command, FileName fileName) {
        switch (command.taskType()) {
            case UPDATE_FIRMWARE -> sensorManufacturerGateway.scheduleFirmwareUpdate(command.sensorId(), fileName);
            case UPDATE_CONFIGURATION -> sensorManufacturerGateway.scheduleConfigurationUpdate(command.sensorId(), fileName);
            default -> throw new IllegalStateException();
        }
    }

    private ScheduleTaskUseCaseResult answer(ScheduleTaskUseCaseResultCode code) {
        return new ScheduleTaskUseCaseResult(code, Optional.empty());
    }

    private ScheduleTaskUseCaseResult answerError(String error) {
        return new ScheduleTaskUseCaseResult(ScheduleTaskUseCaseResultCode.ERROR, Optional.of(error));
    }

    private record SaveSensorCommand(Sensor sensor, TaskType taskType) {}
}
