package com.example.demo.web.controller;

import com.example.demo.repositories.TaskRepository;
import com.example.demo.web.model.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
public class TaskController {
    private final TaskRepository taskRepositoryService;

    public TaskController(TaskRepository taskRepositoryService) {
        this.taskRepositoryService = taskRepositoryService;
    }
    @GetMapping("/task/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        return new ResponseEntity<>(taskRepositoryService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/task/add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto task){
        return new ResponseEntity<>(taskRepositoryService.save(task), HttpStatus.CREATED);
    }

    @PutMapping("/task/edit/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String taskId, @RequestBody TaskDto task){
        if(taskRepositoryService.existsById(taskId)){
            taskRepositoryService.save(task);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/task/remove/{taskId}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable String taskId){
        if(taskRepositoryService.existsById(taskId)){
            taskRepositoryService.deleteById(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
