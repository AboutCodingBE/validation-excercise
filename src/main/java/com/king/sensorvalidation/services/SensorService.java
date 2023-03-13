package com.king.sensorvalidation.services;

import com.king.sensorvalidation.dtos.SensorRequestDto;
import com.king.sensorvalidation.entities.SensorStatus;
import com.king.sensorvalidation.entities.Sensors;
import com.king.sensorvalidation.entities.Tasks;
import com.king.sensorvalidation.mappers.SensorMapper;
import com.king.sensorvalidation.persistence.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepo;

    private final TasksService tasksService;

    public void acceptIncomingShipment(SensorRequestDto requestDto) {
        List<Sensors> sensors = SensorMapper.toSensor(requestDto.sensorsId());
        sensors.stream().forEach(sensor -> {
            Tasks tasks = this.tasksService.scheduleTask(sensor);

            sensor.setStatus_id(2);
            sensor.setTaskCount(1);
            sensor.setStatus(SensorStatus.AWAITING);
            sensor.setQueue(List.of(tasks));

            this.sensorRepo.add(sensor);
        });

    }

    public List<Sensors> fetchSensors(){
        return this.sensorRepo.get().stream().toList();
    }
    public Sensors fetchSensor(String sensorId){
        return this.sensorRepo.get(sensorId);
    }

}
