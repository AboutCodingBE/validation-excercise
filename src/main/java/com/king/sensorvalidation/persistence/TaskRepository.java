package com.king.sensorvalidation.persistence;

import com.king.sensorvalidation.entities.Tasks;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class TaskRepository {


    private final HashMap<String, Tasks> tasks = new HashMap<>();

    public void add(Tasks task) throws DuplicateRequestException {
        if (this.tasks.containsKey(task.getId()))
            throw new DuplicateRequestException("Task already exists.");
        else this.tasks.put(task.getId(), task);
    }

    public Collection<Tasks> get() {
        return this.tasks.values();
    }
    public boolean remove(String taskId){
        this.tasks.remove(taskId);
        return true;
    }
}
