package com.king.sensorvalidation.mappers;


import com.king.sensorvalidation.entities.ActivityStatus;
import com.king.sensorvalidation.entities.Firmwares;
import com.king.sensorvalidation.entities.SensorStatus;
import com.king.sensorvalidation.entities.Sensors;
import com.king.sensorvalidation.services.FileService;
import com.king.sensorvalidation.services.FirmwareService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SensorMapper {
    public static List<Sensors> toSensor(List<String> sensorsIds) {
        return sensorsIds.stream()
                .map(SensorMapper::makeSensor)
                .toList();
    }

    private static Sensors makeSensor(String id){
        Firmwares firmware = FirmwareService.sensorFirmwareWithDefaultConfig();
        firmware.setConfigurationCount(0);

        File files;
        try {
            files = FileService.sensorConfigFile(id, firmware.getCurrentConfiguration());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Sensors.builder()
                .serial(id)
                .type("TS50X")
                .activityStatus(ActivityStatus.DISCONNECTED)
                .status(SensorStatus.IDLE)
                .status_id(1)
                .currentFirmware(firmware)
                .configPath(files.getPath())
                .build();
    }
}