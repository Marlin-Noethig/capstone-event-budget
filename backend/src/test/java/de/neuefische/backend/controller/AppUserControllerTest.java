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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserControllerTest {

    private String userJwt1;
    private String adminJwt1;
    final String userMail1 = "max@muster.de";
    final String adminMail1 = "mimi@muster.de";

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

        userJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "Max", "Muster", "Supercompany", "USER");
        adminJwt1 =generateJwtAndSaveUserToRepo("a1", adminMail1, "Mimi", "Muster", "Supercompany", "ADMIN");
    }

    @Test
    void getInfosOfCurrentlyLoggedInUser() {
        //WHEN
        AppUserInfoDto actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/user/current")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //THEN
        AppUserInfoDto expected = AppUserInfoDto.builder()
                .id("u1")
                .mail(userMail1)
                .firstName("Max")
                .lastName("Muster")
                .company("Supercompany")
                .role("USER").build();

        assertEquals(expected, actual);

    }

    @Test
    void getInfosOfAllUsers_whenAdmin_shouldReturnListOfAllUsers(){
        //WHEN
        List<AppUserInfoDto> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/user/")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(AppUserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //THEN
        AppUserInfoDto expectedUser1 = AppUserInfoDto.builder()
                .id("u1")
                .mail(userMail1)
                .firstName("Max")
                .lastName("Muster")
                .company("Supercompany")
                .role("USER").build();

        AppUserInfoDto expectedUser2 = AppUserInfoDto.builder()
                .id("a1")
                .mail(adminMail1)
                .firstName("Mimi")
                .lastName("Muster")
                .company("Supercompany")
                .role("ADMIN").build();

        List<AppUserInfoDto> expected = List.of(expectedUser1, expectedUser2);

        assertEquals(expected, actual);
    }

    @Test
    void getInfosOfAllUsers_whenUser_shouldReturnError403isForbidden(){
        //WHEN
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/user/")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(userJwt1))
                .exchange()
                //THEN
                .expectStatus().isForbidden();
    }

    private String generateJwtAndSaveUserToRepo(String id, String mail, String firstName, String lastName, String company, String role) {
        String hashedPassword = passwordEncoder.encode("super-safe-password");
        AppUser newUser = AppUser.builder()
                .id(id)
                .mail(mail)
                .password(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .company(company)
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
