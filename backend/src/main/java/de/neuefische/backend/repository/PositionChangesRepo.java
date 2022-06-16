package de.neuefische.backend.repository;

import de.neuefische.backend.model.PositionChange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionChangesRepo extends MongoRepository<PositionChange, String> {
}
