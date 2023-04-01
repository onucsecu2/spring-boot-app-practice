package com.example.demo.web.controller;
import com.example.demo.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/critical-path")
public class CalculationController {
    private final TaskService taskService;

    public CalculationController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<String>> getCriticalTasks(){
        return new ResponseEntity<>(taskService.getCriticalTasks(), HttpStatus.OK);
    }
    @GetMapping("/days")
    public ResponseEntity<Integer> getTotalProjectDays(){
        return new ResponseEntity<>(taskService.getTotalProjectDays(), HttpStatus.OK);
    }
}
