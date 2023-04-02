package org.demo.cpm.repositories;
import org.demo.dto.web.model.CriticalPathDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CriticalPathRepository extends MongoRepository<CriticalPathDto, String> {
}
