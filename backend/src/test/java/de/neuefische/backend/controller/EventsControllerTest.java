package de.neuefische.backend.controller;

import de.neuefische.backend.dto.EventDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.EventsRepo;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventsControllerTest {

    private String adminJwt1;
    private String userJwt1;
    private String userJwt2;

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EventsRepo eventsRepo;

    @BeforeEach
    public void setUp() {
        eventsRepo.deleteAll();
        appUserRepository.deleteAll();

        final String adminMail1 = "admin@tester.de";
        final String userMail1 = "user1@tester.de";
        final String userMail2 = "user2@tester.de";

        adminJwt1 = generateJwtAndSaveUserToRepo("a1", adminMail1, "ADMIN");
        userJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "USER");
        userJwt2 = generateJwtAndSaveUserToRepo("u2", userMail2, "USER");

    }

    @Test
    void getEvents_whenAdmin() {
        //GIVEN
        eventsRepo.insert(testEvent1);
        eventsRepo.insert(testEvent2);

        //WHEN
        List<Event> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/events/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Event.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Event> expected = List.of(expectedEvent1, expectedEvent2);
        assertEquals(expected, actual);
    }

    @Test
    void getEvents_whenUser() {
        //GIVEN
        eventsRepo.insert(testEvent1);
        eventsRepo.insert(testEvent2);

        //WHEN
        List<Event> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/events/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Event.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Event> expected = List.of(expectedEvent1);
        assertEquals(expected, actual);
    }

    @Test
    void postEvent_whenAdmin_shouldBeSuccessfulAndReturnAddedEvent() {
        //GIVEN
        //dto corresponds to testEvent1
        EventDto eventToAdd = EventDto.builder()
                .name("Test Event 1")
                .startDate(LocalDate.parse("2023-04-01"))
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(300)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        //WHEN
        Event actual = webTestClient.post()
                .uri("http://localhost:" + port + "/api/events/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(eventToAdd)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Event.class)
                .returnResult()
                .getResponseBody();

        //THEN
        assertNotNull(actual);
        assertNotNull(actual.getId());
        Event expected = expectedEvent1;
        expected.setId(actual.getId());
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void postEvent_whenUser_shouldReturnClientError403() {
        //GIVEN
        //dto corresponds to testEvent1
        EventDto eventToAdd = EventDto.builder()
                .name("Test Event 1")
                .startDate(LocalDate.parse("2023-04-01"))
                .endDate(LocalDate.parse("2023-04-03"))
                .guests(300)
                .userIds(new ArrayList<>(List.of("u1", "u2")))
                .build();

        //WHEN
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/events/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .bodyValue(eventToAdd)
                .exchange()
                //THEN
                .expectStatus().isForbidden();
    }

    private String generateJwtAndSaveUserToRepo(String id, String mail, String role) {
        String hashedPassword = passwordEncoder.encode("super-safe-password");
        AppUser newUser = AppUser.builder()
                .id(id)
                .mail(mail)
                .password(hashedPassword)
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