package com.king.sensorvalidation.services;

import com.king.sensorvalidation.entities.SensorStatus;
import com.king.sensorvalidation.entities.Sensors;
import com.king.sensorvalidation.entities.TaskStatus;
import com.king.sensorvalidation.entities.Tasks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final String VALID_CONFIGURATION = FirmwareService.getDefaultConfiguration();
    private final Integer TASK_EXECUTION_TIME_LIMIT = 2;
    private final TasksService tasksService;
    private final SensorService sensorService;

    public Collection<Tasks> executeTasks(){
        final Collection<Tasks> tasks = this.tasksService.getTasks();

        tasks.forEach(task -> {
            // completed tasks are ignored.
            if (task.getTaskStatus().equals(TaskStatus.COMPLETED)) return;

            // check if an error occurred.
            if (isPendingBeyondTimeLimit(task)){
                final boolean isDeleted = this.tasksService.deleteTask(task.getId());
                if (isDeleted) this.tasksService.rescheduleTask(task);
            }

            updateFirmware(task);
        });
        return tasks;
    }

    private boolean isPendingBeyondTimeLimit(Tasks task){
        Instant now = Instant.now();
        Long taskInitiationTime = task.getStart();
        long duration = now.getEpochSecond() - taskInitiationTime;

        return duration > TASK_EXECUTION_TIME_LIMIT;
    }

    private void updateFirmware(Tasks task){
        try{
            // get the sensor this task is assigned to.
            final String sensorId = task.getSensor_serial();
            final Sensors sensor = this.sensorService.fetchSensor(sensorId);

            // check if the sensor exists.
            if (sensor == null) return;

            // get the firmware filePath and contents from the firmware configuration.
            String taskFirmwareFilePath = task.getFirmwarePath();
            String contents = FileService.readContents(taskFirmwareFilePath);

            // check if the sensor has valid configuration.
            if(contents.equals(VALID_CONFIGURATION)) return;

            // update the sensor status
            sensor.setStatus(SensorStatus.UPDATING);
            sensor.setStatus_id(3);

            int previousConfigCount = sensor.getCurrentFirmware().getConfigurationCount();
            sensor.getCurrentFirmware().setConfigurationCount(++previousConfigCount);
            final String updatedConfig = "FW:23-09-2022:" + "0" + sensor.getCurrentFirmware().getConfigurationCount();

            FileService.writeToFile(updatedConfig, FileService.getFile(taskFirmwareFilePath));

        }catch (IOException exception){
            this.tasksService.rescheduleTask(task);
            exception.printStackTrace();
        }
    }
}
