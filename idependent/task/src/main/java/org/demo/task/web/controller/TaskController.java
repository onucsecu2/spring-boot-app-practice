package org.demo.task.web.controller;

import org.demo.task.repositories.TaskRepository;
import org.demo.dto.web.model.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskRepository taskRepositoryService;

    public TaskController(TaskRepository taskRepositoryService) {
        this.taskRepositoryService = taskRepositoryService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        return new ResponseEntity<>(taskRepositoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Optional<TaskDto>> getTaskById(@PathVariable String id){
        return new ResponseEntity<>(taskRepositoryService.findById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto task){
        return new ResponseEntity<>(taskRepositoryService.save(task), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable String taskId, @RequestBody TaskDto task){
        if(taskRepositoryService.existsById(taskId)){
            taskRepositoryService.save(task);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable String taskId){
        if(taskRepositoryService.existsById(taskId)){
            taskRepositoryService.deleteById(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
