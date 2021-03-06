package de.neuefische.backend.service;

import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.repository.EventsRepo;
import de.neuefische.backend.repository.PositionsRepo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EventsServiceTest {

    private final EventsRepo eventsRepo = mock(EventsRepo.class);
    private final PositionsRepo positionsRepo = mock(PositionsRepo.class);

    private final EventsService eventsService = new EventsService(eventsRepo, positionsRepo);

    @Test
    void getSubCategories(){
        //GIVEN
        when(eventsRepo.findAll()).thenReturn(List.of(testEvent1, testEvent2));

        //WHEN
        List<Event> actual = eventsService.getEvents();

        //THEN
        List<Event> expected = List.of(expectedEvent1, expectedEvent2);
        verify(eventsRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void addNewEvent_whenNameAlreadyExists_shouldThrowException() {
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
    void addNewEvent_whenSomeFieldIsNull_shouldReturnException() {
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

    @Test
    void updateEvent_whenIdDoesNotExist_shouldThrowException() {
        //GIVEN
        String idOfToUpdate = "1";

        EventDto eventToUpdate = EventDto.builder()
                .name("Test Event 1")
                .startDate(LocalDate.parse("2023-04-01"))
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(123)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            eventsService.updateEventById(idOfToUpdate, eventToUpdate);
        });
    }

    @Test
    void updateEvent_whenStartDateIsNull_shouldThrowException() {
        //GIVEN
        String idOfToUpdate = "1";

        EventDto eventToUpdate = EventDto.builder()
                .name("Test Event 1")
                .startDate(null)
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(123)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        when(eventsRepo.existsById(any())).thenReturn(true);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            eventsService.updateEventById(idOfToUpdate, eventToUpdate);
        });
    }

    @Test
    void deleteEvent_whenIdWrong_shouldThrowException() {
        //GIVEN
        String wrongIdOdToDelete = "1";
        when(eventsRepo.existsById(wrongIdOdToDelete)).thenReturn(false);

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            eventsService.deleteEventById(wrongIdOdToDelete);
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