package nl.suriani.validation.exercise.application.usecase.sensor;

import nl.suriani.validation.exercise.domain.sensor.SensorId;

public record CheckSensorStatusCommand(SensorId sensorId) {
}
