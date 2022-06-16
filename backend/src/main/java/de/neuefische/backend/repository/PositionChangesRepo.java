package de.neuefische.backend.repository;

import de.neuefische.backend.model.PositionChange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionChangesRepo extends MongoRepository<PositionChange, String> {
    List<PositionChange> findAllBySubCategoryId(String subCategoryId);
}
