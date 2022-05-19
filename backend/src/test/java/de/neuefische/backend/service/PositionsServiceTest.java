package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionsServiceTest {

    private final PositionsRepo positionsRepo = mock(PositionsRepo.class);
    private final PositionsService positionsService = new PositionsService(positionsRepo);

    @Test
    void getPositions_whenGetAll_retrieveAll() {
        //GIVEN
        Position position1 = Position.builder()
                .id("1")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();
        Position position2 = Position.builder()
                .id("2")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        when(positionsRepo.findAll()).thenReturn(List.of(position1, position2));
        //WHEN
        List<Position> actual = positionsService.getPositions();

        //THEN
        List<Position> expected = List.of(
                Position.builder()
                        .id("1")
                        .name("Bauzaunplane")
                        .description("Lorem ipsum")
                        .price(50)
                        .amount(10)
                        .tax(19)
                        .build(),
                Position.builder()
                        .id("2")
                        .name("Bauzaunplane")
                        .description("Lorem ipsum")
                        .price(50)
                        .amount(10)
                        .tax(19)
                        .build()
        );

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
        PositionDto newPosition = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        Position actual = positionsService.addNewPosition(newPosition);
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
    void deletePositionById(){

        //WHEN
        positionsService.deletePositionById("1");

        //THEN
        verify(positionsRepo).deleteById("1");

    }


}