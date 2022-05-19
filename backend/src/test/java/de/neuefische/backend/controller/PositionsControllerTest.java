package de.neuefische.backend.controller;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PositionsControllerTest {

    @LocalServerPort
    private  int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp(){
        positionsRepo.deleteAll();
    }

    @Test
    void getPositions() {
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

        positionsRepo.insert(position1);
        positionsRepo.insert(position2);

        //WHEN
        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        Position returnedPosition1 = Position.builder()
                .id("1")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();
        Position returnedPosition2 = Position.builder()
                .id("2")
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        List<Position> expected = List.of(returnedPosition1, returnedPosition2);
        assertEquals(expected, actual);
    }




    @Test
    void postPosition(){
        //GIVEN
        PositionDto newPosition = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        //WHEN
        Position actual = webTestClient.post()
                .uri("http://localhost:" + port + "/api/positions/")
                .bodyValue(newPosition)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        assertNotNull(actual);
        assertNotNull(actual.getId());
        Position expected = Position.builder()
                .id(actual.getId())
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }


}