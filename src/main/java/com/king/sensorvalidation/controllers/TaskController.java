package com.king.sensorvalidation.controllers;

import com.king.sensorvalidation.entities.Tasks;
import com.king.sensorvalidation.services.TasksService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TasksService tasksService;

    @GetMapping
    public List<Tasks> tasks(){
        return this.tasksService.getTasks().stream().toList();
    }
}
