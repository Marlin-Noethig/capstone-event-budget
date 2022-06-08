package de.neuefische.backend.service;

import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.dto.SubCategoryDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.EventsRepo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EventsServiceTest {

    private final EventsRepo eventsRepo = mock(EventsRepo.class);

    private final EventsService eventsService = new EventsService(eventsRepo);

    @Test
    void addNewEvent_whenSomeFieldIsNull_shouldThrowException() {
        //GIVEN
        EventDto eventToAdd = EventDto.builder()
                .name("Test Event 1")
                .startDate(LocalDate.parse("2023-04-01"))
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(123)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        when(eventsRepo.findByName(eventToAdd.getName())).thenReturn(testEvent1);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            eventsService.addNewEvent(eventToAdd);
        });
    }

    @Test
    void addNewEvent_whenNameAlreadyExits_shouldReturnException() {
        //GIVEN
        EventDto eventToAdd = EventDto.builder()
                .name("Test Event 1")
                .startDate(null)
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(123)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            eventsService.addNewEvent(eventToAdd);
        });
    }





    //Global test variables
    Event testEvent1 = Event.builder()
            .id("a")
            .name("Test Event 1")
            .startDate(LocalDate.parse("2023-04-01"))
            .endDate(LocalDate.parse("2023-04-03"))
            .guests(300)
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

    Event testEvent2 = Event.builder()
            .id("b")
            .name("Test Event 3")
            .startDate(LocalDate.parse("2023-04-01"))
            .endDate(LocalDate.parse("2023-04-03"))
            .guests(400)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();

    Event expectedEvent1 = Event.builder()
            .id("a")
            .name("Test Event 1")
            .startDate(LocalDate.parse("2023-04-01"))
            .endDate(LocalDate.parse("2023-04-03"))
            .guests(300)
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

    Event expectedEvent2 = Event.builder()
            .id("b")
            .name("Test Event 3")
            .startDate(LocalDate.parse("2023-04-01"))
            .endDate(LocalDate.parse("2023-04-03"))
            .guests(400)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();
}