package com.king.sensorvalidation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensors {

    public static final String VALID_FIRM_VERSION = "FW:23-09-2022:0";

    private String serial;
    private String type = "TS50X";
    private int status_id;
    private SensorStatus status;
    private Firmwares currentFirmware;
    private Tasks nextTask;
    private int taskCount;
    private ActivityStatus activityStatus;
    private List<Tasks> queue;
    private String configPath;

    public Sensors(String id) {
        this.serial = id;
        this.type = "TS50X";
    }

}