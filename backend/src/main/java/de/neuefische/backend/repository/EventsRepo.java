package de.neuefische.backend.repository;

import de.neuefische.backend.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepo extends MongoRepository<Event, String> {
    List<Event> findAllByUserIdsContaining(String idOfCurrentUser);
    Event findByName(String name);
}
