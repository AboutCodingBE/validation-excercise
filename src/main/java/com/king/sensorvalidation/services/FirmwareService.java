package com.king.sensorvalidation.services;


import com.king.sensorvalidation.entities.Firmwares;

public class FirmwareService {
    private static final String DEFAULT_CONFIGURATION = "FW:23-09-2022:00";

    public static Firmwares sensorFirmwareWithDefaultConfig(){
        Firmwares firmware = new Firmwares();
        firmware.setCurrentConfiguration(DEFAULT_CONFIGURATION);
        return firmware;
    }

    public static String getDefaultConfiguration(){
        return DEFAULT_CONFIGURATION;
    }

}