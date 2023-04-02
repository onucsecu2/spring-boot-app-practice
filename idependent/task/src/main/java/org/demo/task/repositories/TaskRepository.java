package org.demo.task.repositories;

import org.demo.dto.web.model.TaskDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<TaskDto, String> {
}
