package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionsServiceTest {

    private final PositionsRepo positionsRepo = mock(PositionsRepo.class);
    private final SubCategoriesService subCategoriesService = mock(SubCategoriesService.class);
    private final EventsService eventsService = mock(EventsService.class);
    private final PositionChangesService positionChangesService = mock(PositionChangesService.class);
    private final PositionsService positionsService = new PositionsService(positionsRepo, subCategoriesService, eventsService, positionChangesService);

    @Test
    void getPositions_whenGetAll_retrieveAll() {
        //GIVEN
        when(positionsRepo.findAll()).thenReturn(List.of(testPosition1, testPosition2));
        //WHEN
        List<Position> actual = positionsService.getPositions();
        //THEN
        List<Position> expected = List.of(expectedPosition1, expectedPosition2);
        verify(positionsRepo).findAll();
        assertEquals(expected, actual);

    }

    @Test
    void addNewPosition() {
        //GIVEN
        Position positionToAdd = Position.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        when(positionsRepo.insert(positionToAdd)).thenReturn(
                Position.builder()
                        .id("test-id-123")
                        .name("Bauzaunplane")
                        .description("Lorem ipsum")
                        .price(50)
                        .amount(10)
                        .tax(19)
                        .build());
        //WHEN

        Position actual = positionsService.addNewPosition(dtoPosition1);
        //THEN
        Position expected = Position.builder()
                .id("test-id-123")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();
        verify(positionsRepo).insert(positionToAdd);
        assertEquals(expected, actual);
    }

    @Test
    void addNewPosition_whenNameIsNull_shouldReturnException() {
        //GIVEN
        PositionDto positionToAdd = PositionDto.builder()
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            positionsService.addNewPosition(positionToAdd);
        });
    }

    @Test
    void updatePositionById_IdExists() {
        //GIVEN
        String idOfToUpdate = "1";

        PositionDto positionToUpdate = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        Position updatedPosition = Position.builder()
                .id("1")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7)
                .build();
        when(positionsRepo.existsById(any())).thenReturn(true);
        when(positionsRepo.save(any())).thenReturn(updatedPosition);

        //WHEN
        Position actual = positionsService.updatePositionById(idOfToUpdate, positionToUpdate, currentUser);

        //THEN
        assertThat(actual, is(updatedPosition));
    }

    @Test
    void updatePositionById_IdDoesNotExist() {
        //GIVEN
        String idOfToUpdate = "1";

        PositionDto positionToUpdate = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            positionsService.updatePositionById(idOfToUpdate, positionToUpdate, currentUser);
        });
    }

    @Test
    void updatePositionById_amountIs0_shouldThrowException() {
        //GIVEN
        String idOfToUpdate = "1";

        PositionDto positionToUpdate = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(0)
                .tax(19)
                .build();

        Position updatedPosition = Position.builder()
                .id("1")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7)
                .build();
        when(positionsRepo.existsById(any())).thenReturn(true);
        when(positionsRepo.save(any())).thenReturn(updatedPosition);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            positionsService.updatePositionById(idOfToUpdate, positionToUpdate, currentUser);
        });
    }

    @Test
    void deletePositionById() {
        //GIVEN
        String idOfToDelete= "1";

        when(positionsRepo.existsById(idOfToDelete)).thenReturn(true);

        //WHEN
        positionsService.deletePositionById(idOfToDelete);

        //THEN
        verify(positionsRepo).deleteById("1");

    }

    @Test
    void deletePositionById_whenIdWrong_shouldThrowException(){
        //GIVEN
        String wrongIdOfToDelete = "1";

        when(positionsRepo.existsById(wrongIdOfToDelete)).thenReturn(false);

        //WHEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            positionsService.deletePositionById(wrongIdOfToDelete);
        });
    }

    //global dummy objects for multiple set ups
    Position testPosition1 = Position.builder()
            .id("1")
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();
    Position testPosition2 = Position.builder()
            .id("2")
            .name("Bohmaschine")
            .description("Lorem ipsum")
            .price(10)
            .amount(2)
            .tax(19)
            .build();

    //global dummy objects for multiple expectations
    Position expectedPosition1 = Position.builder()
            .id("1")
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();
    Position expectedPosition2 = Position.builder()
            .id("2")
            .name("Bohmaschine")
            .description("Lorem ipsum")
            .price(10)
            .amount(2)
            .tax(19)
            .build();

    //global dummy dto for multiple inserts
    PositionDto dtoPosition1 = PositionDto.builder()
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();

    AppUserInfoDto currentUser = AppUserInfoDto.builder()
            .id("123")
            .mail("mimi@muster.de")
            .firstName("Mimi")
            .lastName("muster")
            .company("Super Company")
            .role("ADMIN")
            .build();

}