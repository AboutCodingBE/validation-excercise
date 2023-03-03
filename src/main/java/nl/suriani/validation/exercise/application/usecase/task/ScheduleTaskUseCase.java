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
        sensorRepository.findById(command.sensorId());
        return answer(ScheduleTaskUseCaseResultCode.SENSOR_NOT_FOUND);
    }

    private ScheduleTaskUseCaseResult answer(ScheduleTaskUseCaseResultCode code) {
        return new ScheduleTaskUseCaseResult(code, Optional.empty());
    }

    private ScheduleTaskUseCaseResult answerError(String error) {
        return new ScheduleTaskUseCaseResult(ScheduleTaskUseCaseResultCode.ERROR, Optional.of(error));
    }
}
