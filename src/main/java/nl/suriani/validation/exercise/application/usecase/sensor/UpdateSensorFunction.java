package nl.suriani.validation.exercise.application.usecase.sensor;

import nl.suriani.validation.exercise.application.port.sensor.SensorAtManufacturer;
import nl.suriani.validation.exercise.domain.sensor.Sensor;

import java.util.function.BiFunction;

public class UpdateSensorFunction implements BiFunction<Sensor, SensorAtManufacturer, Sensor> {

    @Override
    public Sensor apply(Sensor sensor, SensorAtManufacturer sensorAtManufacturer) {
        if (!sensor.id().equals(sensorAtManufacturer.id())) {
            throw new IllegalStateException("Ids must be equal in order to update the sensor information");
        }

        var updatedSensor = merge(sensor, sensorAtManufacturer);
        return updatedSensor;
    }

    private Sensor merge(Sensor sensor, SensorAtManufacturer sensorAtManufacturer) {
        return new Sensor(sensor.id(),
                sensorAtManufacturer.firmware(),
                sensorAtManufacturer.configuration(),
                sensorAtManufacturer.tasksStatus(),
                sensorAtManufacturer.activityStatus(),
                sensor.events());
    }
}
