package de.neuefische.backend.controller;

import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.security.service.utils.AuthUtils;
import de.neuefische.backend.service.EventsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/events/")
public class EventsController {

    private final EventsService eventsService;
    
        @Autowired
        public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public List<Event> getEvents(Authentication authentication) {
        String roleOfCurrentUser = authentication.getAuthorities().toArray()[0].toString();

        if (roleOfCurrentUser.equals("ADMIN")) {
            return eventsService.getEvents();
        } else {
            String idOfCurrentUser = AuthUtils.getIdOfCurrentUser(authentication);
            return eventsService.getEventsByUserId(idOfCurrentUser);
        }
    }

    @PostMapping
    public Event postEvent(@RequestBody EventDto newEvent){
            return eventsService.addNewEvent(newEvent);
    }

    @PutMapping("{id}")
    public Event putEventById(@PathVariable String id, @RequestBody EventDto eventToUpdate){
            return eventsService.updateEventById(id, eventToUpdate);
    }

    @DeleteMapping("{id}")
    public void deleteEventById(@PathVariable String id){
            eventsService.deleteEventById(id);
    }
}
