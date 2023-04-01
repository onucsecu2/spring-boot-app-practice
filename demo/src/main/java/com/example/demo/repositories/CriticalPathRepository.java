package com.example.demo.repositories;

import com.example.demo.web.model.CriticalPathDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CriticalPathRepository extends MongoRepository<CriticalPathDto, String> {
}
