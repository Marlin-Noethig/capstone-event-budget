package de.neuefische.backend.service;


import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.repository.EventsRepo;
import de.neuefische.backend.repository.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventsService {

    private final EventsRepo eventsRepo;
    private final PositionsRepo positionsRepo;

    @Autowired
    public EventsService(EventsRepo eventsRepo, PositionsRepo positionsRepo) {
        this.eventsRepo = eventsRepo;
        this.positionsRepo = positionsRepo;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Event> getEvents() {
        return eventsRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<Event> getEventsByUserId(String idOfCurrentUser) {
        return eventsRepo.findAllByUserIdsContaining(idOfCurrentUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Event addNewEvent(EventDto eventToAdd) {
        if (eventsRepo.findByName(eventToAdd.getName()) != null) {
            throw new IllegalArgumentException("Event with name " + eventToAdd.getName() + " already exists.");
        }

        if (eventToAdd.getName() == null || eventToAdd.getStartDate() == null || eventToAdd.getEndDate() == null || eventToAdd.getGuests() <= 0) {
            throw new IllegalArgumentException("Newly added event is incomplete. Please make sure that every field has a value.");
        }

        Event newEvent = new Event(eventToAdd);
        return eventsRepo.insert(newEvent);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Event updateEventById(String id, EventDto eventToUpdate) {
        if (!eventsRepo.existsById(id)) {
            throw new NoSuchElementException("No Event with Id " + id + " found to be updated.");
        }
        if (eventToUpdate.getName() == null || eventToUpdate.getStartDate() == null || eventToUpdate.getEndDate() == null || eventToUpdate.getGuests() <= 0) {
            throw new IllegalArgumentException("Updated event is incomplete. Please make sure that every field has a value.");
        }

        Event updatedEvent = new Event(eventToUpdate);
        updatedEvent.setId(id);

        return eventsRepo.save(updatedEvent);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEventById(String id) {
        if (!eventsRepo.existsById(id)){
            throw new NoSuchElementException("Event with Id " + id + " does not exist.");
        }

        eventsRepo.deleteById(id);
        positionsRepo.deleteAllByEventId(id);
    }
}
