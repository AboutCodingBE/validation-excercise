package com.king.sensorvalidation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class Tasks {
    private String id;
    private String sensor_serial;
    private TaskType type;
    private TaskStatus taskStatus;
    private Integer attemptCount;
    private String fileId;
    private String configFilePath;
    private String firmwarePath;
    private int statusName;
    private Long start;
    private Long stop;
    private Long duration;

    Tasks(){
        this.taskStatus = TaskStatus.PENDING;
    }

}
