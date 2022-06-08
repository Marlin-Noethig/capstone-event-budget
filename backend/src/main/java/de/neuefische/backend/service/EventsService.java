package de.neuefische.backend.service;


import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.repository.EventsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    private final EventsRepo eventsRepo;


    @Autowired
    public EventsService(EventsRepo eventsRepo) {
        this.eventsRepo = eventsRepo;
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
}
