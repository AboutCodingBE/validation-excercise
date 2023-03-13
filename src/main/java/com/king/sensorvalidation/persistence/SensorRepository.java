package com.king.sensorvalidation.persistence;

import com.king.sensorvalidation.entities.Sensors;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Repository
public class SensorRepository {

    private final HashMap<String, Sensors> sensorDb = new HashMap<>();


    public void add(Sensors sensors) throws DuplicateRequestException {
        if (this.sensorDb.containsKey(sensors.getSerial()))
            throw new DuplicateRequestException("Sensor with serial already exists.");
        this.sensorDb.put(sensors.getSerial(), sensors);
    }


    public void addAll(List<Sensors> sensors){
        sensors.forEach(this::add);
    };

    public Collection<Sensors> get(){
        return this.sensorDb.values();
    }

    public Sensors get(String sensorId){
        return this.sensorDb.get(sensorId);
    }

}
