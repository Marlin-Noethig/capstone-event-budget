package de.neuefische.backend.security.controller;

import de.neuefische.backend.security.dto.AppUserLoginDto;
import de.neuefische.backend.security.model.AppUser;
import de.neuefische.backend.security.repository.AppUserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserAuthControllerTest {

    @Value("${neuefische.capstone-event-budget.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        appUserRepository.deleteAll();
    }

    @Test
    void login_whenValid_return_validJwt() {
        //GIVEN
        AppUser testUser = createTestUserInRepoAndGet();

        //WHEN
        String jwt = webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUserLoginDto.builder()
                        .mail("test@testmail.com")
                        .password("suer-safe-password")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        //THEN
        String expected = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        assertEquals(expected, testUser.getMail());

    }

    @Test
    void login_whenInvalidCredentials_thenReturnForbiddenError() {
        //GIVEN
        createTestUserInRepoAndGet();

        //WHEN/
        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUser.builder()
                        .mail("test@testmail.com")
                        .password("wrong-password")
                        .build())
                .exchange()
                //THEN
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }


    private AppUser createTestUserInRepoAndGet() {
        String hashedPassword = passwordEncoder.encode("suer-safe-password");

        AppUser testUser = AppUser.builder()
                .mail("test@testmail.com")
                .password(hashedPassword)
                .build();

        appUserRepository.insert(testUser);
        return testUser;
    }
}