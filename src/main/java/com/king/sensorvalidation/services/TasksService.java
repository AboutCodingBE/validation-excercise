package com.king.sensorvalidation.services;


import com.king.sensorvalidation.entities.Sensors;
import com.king.sensorvalidation.entities.TaskStatus;
import com.king.sensorvalidation.entities.TaskType;
import com.king.sensorvalidation.entities.Tasks;
import com.king.sensorvalidation.persistence.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TasksService {

    private final TaskRepository taskRepository;

    public Tasks scheduleTask(Sensors sensor){

        System.out.println("Task scheduling for sensors");
        System.out.println(sensor.toString());
        Long instant = new Date().getTime();

        Tasks task = Tasks.builder()
                .sensor_serial(sensor.getSerial())
                .taskStatus(TaskStatus.ONGOING)
                .type(TaskType.UPDATE_CONFIGURATION)
                .configFilePath(sensor.getConfigPath())
                .firmwarePath(sensor.getConfigPath())
                .fileId(String.valueOf(sensor.getConfigPath().hashCode()))
                .attemptCount(1)
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
                .start(instant)
                .build();

        this.taskRepository.add(task);

        return task;
    }

    public Collection<Tasks> getTasks(){
        return this.taskRepository.get();
    }

    public void rescheduleTask(Tasks tasks){
        tasks.setAttemptCount(tasks.getAttemptCount()+1);
        tasks.setStart(Instant.now().getEpochSecond());
        this.taskRepository.add(tasks);
    }

    public boolean deleteTask(String taskId){
        return this.taskRepository.remove(taskId);
    }

}
