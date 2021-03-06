package de.neuefische.backend.repository;


import de.neuefische.backend.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PositionsRepo extends MongoRepository<Position, String> {

    List<Position> findAllBySubCategoryIdIsInAndEventIdIsIn(ArrayList<String> subCategoryIds, ArrayList<String> eventIds);
    void deleteAllBySubCategoryId(String subCategoryId);
    void deleteAllByEventId(String eventId);
}
