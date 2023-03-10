package nl.suriani.validation.exercise.application.usecase.sensor;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.application.port.sensor.SensorRepository;
import nl.suriani.validation.exercise.application.port.sensor.SensorManufacturerGateway;
import nl.suriani.validation.exercise.domain.sensor.Sensor;

import java.util.List;

import static nl.suriani.validation.exercise.application.usecase.sensor.UpdateSensorStatusUseCaseResultCode.*;

public class UpdateSensorStatusUseCase {

    private final SensorRepository sensorRepository;
    private final SensorManufacturerGateway sensorManufacturerGateway;
    private static final UpdateSensorFunction UPDATE_SENSOR_FUNCTION = new UpdateSensorFunction();

    public UpdateSensorStatusUseCase(SensorRepository sensorRepository, SensorManufacturerGateway sensorManufacturerGateway) {
        this.sensorRepository = sensorRepository;
        this.sensorManufacturerGateway = sensorManufacturerGateway;
    }

    public UpdateSensorStatusUseCaseResult apply(UpdateSensorStatusCommand command) {
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

        if (sensorManufacturerGateway.needsFirmwareUpdate(sensorAtManufacturer)) {
            updateSensorAndSave(sensor.firmwareUpdateRequired(), sensorAtManufacturer);
            return answer(FIRMWARE_UPDATE_REQUIRED);
        };

        if (sensorManufacturerGateway.needsConfigurationUpdate(sensorAtManufacturer)) {
            updateSensorAndSave(sensor.configurationUpdateRequired(), sensorAtManufacturer);
            return answer(CONFIGURATION_UPDATE_REQUIRED);
        }

        updateSensorAndSave(sensor, sensorAtManufacturer);
        return answer(NO_ACTION_NEEDED);
    }

    private void updateSensorAndSave(Sensor sensor, SensorAtManufacturer sensorAtManufacturer) {
        final var updatedSensor = UPDATE_SENSOR_FUNCTION.apply(sensor, sensorAtManufacturer);
        sensorRepository.save(updatedSensor);
    }

    private static UpdateSensorStatusUseCaseResult answer(UpdateSensorStatusUseCaseResultCode updateFirmware, List<String> errors) {
        return new UpdateSensorStatusUseCaseResult(updateFirmware, errors);
    }

    private static UpdateSensorStatusUseCaseResult answer(UpdateSensorStatusUseCaseResultCode updateFirmware) {
        return new UpdateSensorStatusUseCaseResult(updateFirmware, List.of());
    }

    private void saveSensorAsItIsToRefreshLastUpdatedMetadata(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
