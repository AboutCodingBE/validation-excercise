package nl.suriani.validation.exercise.application.usecase.task;

import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.task.TaskRepository;

import java.util.Optional;

public class ScheduleTaskUseCase {
    private SensorRepository sensorRepository;
    private TaskRepository taskRepository;
    private SensorManufacturerGateway sensorManufacturerGateway;

    public ScheduleTaskUseCaseResult apply(ScheduleTaskCommand command) {
        var maybeSensor = sensorRepository.findById(command.sensorId());
        if (maybeSensor.isEmpty()) {
            return answer(ScheduleTaskUseCaseResultCode.SENSOR_NOT_FOUND);
        }
        sensorManufacturerGateway.findSensorById(command.sensorId());
        return answer(ScheduleTaskUseCaseResultCode.SENSOR_NOT_KNOWN_BY_MANUFACTURER);
    }

    private ScheduleTaskUseCaseResult answer(ScheduleTaskUseCaseResultCode code) {
        return new ScheduleTaskUseCaseResult(code, Optional.empty());
    }

    private ScheduleTaskUseCaseResult answerError(String error) {
        return new ScheduleTaskUseCaseResult(ScheduleTaskUseCaseResultCode.ERROR, Optional.of(error));
    }
}
