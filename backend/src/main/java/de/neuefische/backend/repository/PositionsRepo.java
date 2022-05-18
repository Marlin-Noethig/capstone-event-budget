package de.neuefische.backend.repository;


import de.neuefische.backend.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionsRepo extends MongoRepository<Position, String> {

}
