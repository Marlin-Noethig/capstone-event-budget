package de.neuefische.backend.controller;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.security.dto.AppUserDto;
import de.neuefische.backend.security.model.AppUser;
import de.neuefische.backend.security.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PositionsControllerTest {

    private String jwt1;
    private final String userMail1 = "test@tester.de";


    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp() {
        positionsRepo.deleteAll();
        appUserRepository.deleteAll();

        jwt1 = generateJwtAndSaveUserToRepo(userMail1);

    }

    @Test
    void getPositions() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPposition2);

        //WHEN
        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(jwt1))
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
    void getPositions_whenWrongToken_shouldReturnForbidden() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPposition2);

        String wrongToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Indyb25nIHN0dWZmIiwiaWF0IjoxNTE2MjM5MDIyfQ._L8LHFgSbnXxLLT1Qhni-9IZsXaUG-t0Y0qU9gabqhw";

        //WHEN
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(wrongToken))
                .exchange()
                //THEN
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
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
                .headers(http -> http.setBearerAuth(jwt1))
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
    void postPosition_whenNameNull_shouldThrow422Status(){
        //GIVEN
        //dto for testPosition1 without name
        PositionDto newPosition = PositionDto.builder()
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        //WHEN
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(jwt1))
                .bodyValue(newPosition)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void putPositionById_successful() {
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
                .headers(http -> http.setBearerAuth(jwt1))
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
    void putPositionById_unsuccessful() {
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
                .headers(http -> http.setBearerAuth(jwt1))
                .bodyValue(updatedPosition)
                .exchange()
                .expectStatus().isNotFound(); //needs to be refactored after adding appropriate controller advice

    }

    @Test
    void deletePositionById() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPposition2);

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .headers(http -> http.setBearerAuth(jwt1))
                .exchange()
                .expectStatus().is2xxSuccessful();

        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(jwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition2);
        assertEquals(expected, actual);

    }

    private String generateJwtAndSaveUserToRepo(String mail) {
        String hashedPassword = passwordEncoder.encode("super-safe-password");
        AppUser newUser = AppUser.builder()
                .mail(mail)
                .password(hashedPassword)
                .build();
        appUserRepository.insert(newUser);

        return webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUserDto.builder()
                        .mail(mail)
                        .password("super-safe-password")
                        .build())
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
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