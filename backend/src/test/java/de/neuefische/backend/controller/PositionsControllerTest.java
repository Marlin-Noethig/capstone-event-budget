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
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp() {
        positionsRepo.deleteAll();
    }

    @Test
    void getPositions() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPposition2);

        //WHEN
        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition1, expectedPosition2);
        assertEquals(expected, actual);
    }


    @Test
    void postPosition() {
        //GIVEN
        //dto for testPosition1
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
        Position expected = testPosition1;
        expected.setId(actual.getId());
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void putPositionById_successful(){
        //GIVEN
        positionsRepo.insert(testPosition1);

        PositionDto updatedPosition = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7) // changed tax in update
                .build();

        //WHEN
        Position actual = webTestClient.put()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .bodyValue(updatedPosition)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Position.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        Position expected = expectedPosition1;
        expected.setTax(7);
        assertEquals(expected, actual);
    }

    @Test
    void putPositionById_unsuccessful(){
        //GIVEN
        positionsRepo.insert(testPosition1);
        String wrongId = "123";

        PositionDto updatedPosition = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7) // changed tax in update
                .build();

        //WHEN
        webTestClient.put()
                .uri("http://localhost:" + port + "/api/positions/" + wrongId)
                .bodyValue(updatedPosition)
                .exchange()
                .expectStatus().is5xxServerError(); //needs to be refactored after adding appropriate controller advice

    }


    @Test
    void deletePositionById(){
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPposition2);

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();

        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition2);
        assertEquals(expected, actual);

    }


    //global dummy Objects for build up / matching below
    Position testPosition1 = Position.builder()
            .id("1")
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();
    Position testPposition2 = Position.builder()
            .id("2")
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();

    //global dummy Objects for expectations / matching above
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
            .name("Bauzaunplane")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .build();


}