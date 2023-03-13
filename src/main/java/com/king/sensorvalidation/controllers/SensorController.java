package com.king.sensorvalidation.controllers;


import com.king.sensorvalidation.dtos.SensorRequestDto;
import com.king.sensorvalidation.dtos.SensorResponseDto;
import com.king.sensorvalidation.entities.Tasks;
import com.king.sensorvalidation.services.SensorService;
import com.king.sensorvalidation.services.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;
    private final ValidationService validationService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void acceptSensorsId(@RequestBody SensorRequestDto sensorIds){
        this.sensorService.acceptIncomingShipment(sensorIds);


        this.validationService.executeTasks();

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SensorResponseDto> sensors(){
        return this.sensorService.fetchSensors()
                .stream()
                .map(SensorResponseDto::toResponse)
                .toList();
    }


}
