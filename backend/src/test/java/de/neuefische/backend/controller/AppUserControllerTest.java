package de.neuefische.backend.controller;

import de.neuefische.backend.security.dto.AppUserInfoDto;
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

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserControllerTest {

    private String adminJwt1;
    final String userMail1 = "test@tester.de";

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    public void setUp() {
        appUserRepository.deleteAll();

        adminJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "USER");
    }

    @Test
    void getCurrentUser() {
        //WHEN
        AppUserInfoDto actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/user/current")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //THEN
        AppUserInfoDto expected = AppUserInfoDto.builder().id("u1").mail(userMail1).build();

        assertEquals(expected, actual);

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
}