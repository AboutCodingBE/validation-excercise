package com.king.sensorvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SensorValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorValidationApplication.class, args);
    }

    /* TODO: everytime the application starts create a file with the latest configuration. */
}
