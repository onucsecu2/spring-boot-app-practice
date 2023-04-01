package com.example.demo.repositories;

import com.example.demo.web.model.TaskDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<TaskDto, String> {
}
