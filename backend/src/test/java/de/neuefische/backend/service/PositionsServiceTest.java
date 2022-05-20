package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionsServiceTest {

    private final PositionsRepo positionsRepo = mock(PositionsRepo.class);
    private final PositionsService positionsService = new PositionsService(positionsRepo);

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
    void updatePositionById_IdExists(){
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
        Position actual = positionsService.updatePositionById(idOfToUpdate, positionToUpdate);

        //THEN
        assertThat(actual, is(updatedPosition));
    }

    @Test
    void updatePositionById_IdDoesNotExist(){
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
           positionsService.updatePositionById(idOfToUpdate, positionToUpdate);
        });
    }

    @Test
    void deletePositionById(){

        //WHEN
        positionsService.deletePositionById("1");

        //THEN
        verify(positionsRepo).deleteById("1");

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

}