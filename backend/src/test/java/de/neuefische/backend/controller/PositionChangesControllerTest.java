package de.neuefische.backend.controller;


import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.PositionChange;
import de.neuefische.backend.repository.PositionChangesRepo;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.security.dto.AppUserLoginDto;
import de.neuefische.backend.security.model.AppUser;
import de.neuefische.backend.security.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PositionChangesControllerTest {

    private String adminJwt1;

    @LocalServerPort
    private int port;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PositionChangesRepo positionChangesRepo;

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp(){
        appUserRepository.deleteAll();
        positionChangesRepo.deleteAll();
        positionsRepo.deleteAll();

        adminJwt1 = generateJwtAndSaveUserToRepo("a1", "admin@tewster.de", "ADMIN");
    }

    @Test
    void getPositionChangesById_whenMethodIsUpdate(){
        //GIVEN
        positionsRepo.insert(testPosition1);

        PositionDto positionToUpdate = PositionDto.builder()
                .name("Starkstromanschluss")
                .description("Lorem ipsum")
                .price(100) // price changed from 50 to 100
                .amount(10)
                .tax(19)
                .subCategoryId("aaa")
                .eventId("111")
                .build();

        webTestClient.put()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(positionToUpdate)
                .exchange()
                .expectStatus().is2xxSuccessful();

        //WHEN
        List<PositionChange> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/position-changes?subCategoryId=" + testPosition1.getSubCategoryId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(PositionChange.class)
                .returnResult()
                .getResponseBody();

        //THEN
        Position changedData = testPosition1;
        changedData.setPrice(100);

        PositionChange expectedChange1 = PositionChange.builder()
                .id(actual.get(0).getId())
                .timestamp(actual.get(0).getTimestamp())
                .method("UPDATE")
                .data(changedData)
                .userInfo("Max Muster (Test Company)")
                .positionId(testPosition1.getId())
                .subCategoryId(testPosition1.getSubCategoryId())
                .build();

        List<PositionChange> expected = List.of(expectedChange1);
        assertEquals(expected, actual);
    }

    private String generateJwtAndSaveUserToRepo(String id, String mail, String role) {
        String hashedPassword = passwordEncoder.encode("super-safe-password");
        AppUser newUser = AppUser.builder()
                .id(id)
                .mail(mail)
                .password(hashedPassword)
                .firstName("Max")
                .lastName("Muster")
                .company("Test Company")
                .role(role)
                .build();
        appUserRepository.insert(newUser);

        return webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUserLoginDto.builder()
                        .mail(mail)
                        .password("super-safe-password")
                        .build())
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    Position testPosition1 = Position.builder()
            .id("1")
            .name("Starkstromanschluss")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId("aaa")
            .eventId("111")
            .build();

}