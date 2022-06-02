package de.neuefische.backend.controller;

import de.neuefische.backend.model.Event;
import de.neuefische.backend.security.service.utils.AuthUtils;
import de.neuefische.backend.service.EventsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
