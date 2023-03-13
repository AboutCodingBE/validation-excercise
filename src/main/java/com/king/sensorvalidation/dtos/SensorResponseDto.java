package com.king.sensorvalidation.dtos;

import com.king.sensorvalidation.entities.Sensors;
import com.king.sensorvalidation.entities.Tasks;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
public class SensorResponseDto {

    public String serial;
    public String type;
    public int status_id;
    public String current_configuration;
    public String current_firmware;
    public String status_name;
    public Tasks next_task;
    public int task_count;
    public String activity_status;
    public List<Tasks> tasks_queue;

    public static SensorResponseDto toResponse(Sensors sensors){
        final String absoluteConfigPath = sensors.getConfigPath();
        final String configPath = absoluteConfigPath
                .substring(absoluteConfigPath.lastIndexOf("/")+1, absoluteConfigPath.length()-1);

        return SensorResponseDto
                .builder()
                .serial(sensors.getSerial())
                .status_id(2)//TODO work on this don't hard code this.
                .activity_status(sensors.getActivityStatus().name())
                .next_task(sensors.getNextTask())
                .status_name(sensors.getStatus().name())
                .current_configuration(configPath)
                .tasks_queue(sensors.getQueue())
                .task_count(sensors.getTaskCount())
                .current_firmware(sensors.getCurrentFirmware().getCurrentConfiguration())
                .build();
    }
}
