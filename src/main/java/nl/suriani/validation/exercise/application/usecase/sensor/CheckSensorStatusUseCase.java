package nl.suriani.validation.exercise.application.usecase.sensor;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.domain.sensor.Sensor;

import java.util.List;

import static nl.suriani.validation.exercise.application.usecase.sensor.CheckSensorStatusUseCaseResultCode.*;

public class CheckSensorStatusUseCase {

    private final SensorRepository sensorRepository;
    private final SensorManufacturerGateway sensorManufacturerGateway;
    private static final UpdateSensorFunction UPDATE_SENSOR_FUNCTION = new UpdateSensorFunction();

    public CheckSensorStatusUseCase(SensorRepository sensorRepository, SensorManufacturerGateway sensorManufacturerGateway) {
        this.sensorRepository = sensorRepository;
        this.sensorManufacturerGateway = sensorManufacturerGateway;
    }

    public CheckSensorStatusUseCaseResult apply(CheckSensorStatusCommand command) {
        var sensorId = command.sensorId();
        var maybeSensor = sensorRepository.findById(sensorId);

        if (maybeSensor.isEmpty()) {
            return answer(NOT_REGISTERED);
        }

        final var sensor = maybeSensor.get();

        saveSensorAsItIsToRefreshLastUpdatedMetadata(sensor);

        var maybeSensorAtManufacturer = sensorManufacturerGateway.findSensorById(sensorId);

        if (maybeSensorAtManufacturer.isEmpty()) {
            return answer(ERROR, List.of("Sensor not known by manufacturer"));
        }

        final var sensorAtManufacturer = maybeSensorAtManufacturer.get();
        updateSensorAndSave(sensor, sensorAtManufacturer);

        if (sensorManufacturerGateway.needsFirmwareUpdate(sensorAtManufacturer)) {
            return answer(UPDATE_FIRMWARE);
        };

        if (sensorManufacturerGateway.needsConfigurationUpdate(sensorAtManufacturer)) {
            return answer(UPDATE_CONFIGURATION);
        }

        return answer(NO_ACTION_NEEDED);
    }

    private void updateSensorAndSave(Sensor sensor, SensorAtManufacturer sensorAtManufacturer) {
        final var updatedSensor = UPDATE_SENSOR_FUNCTION.apply(sensor, sensorAtManufacturer);
        sensorRepository.save(updatedSensor);
    }

    private static CheckSensorStatusUseCaseResult answer(CheckSensorStatusUseCaseResultCode updateFirmware, List<String> errors) {
        return new CheckSensorStatusUseCaseResult(updateFirmware, errors);
    }

    private static CheckSensorStatusUseCaseResult answer(CheckSensorStatusUseCaseResultCode updateFirmware) {
        return new CheckSensorStatusUseCaseResult(updateFirmware, List.of());
    }

    private void saveSensorAsItIsToRefreshLastUpdatedMetadata(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
