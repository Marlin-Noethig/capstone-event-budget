package de.neuefische.backend.controller;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
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
class MainCategoriesControllerTest {

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
    private MainCategoriesRepo mainCategoriesRepo;

    @BeforeEach
    public void setUp() {
        mainCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();

        jwt1 = generateJwtAndSaveUserToRepo(userMail1);
    }

    @Test
    void getMainCategories() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<MainCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/")
                .headers(http -> http.setBearerAuth(jwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MainCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory1, expectedMainCategory2);
        assertEquals(expected, actual);
    }

    private String generateJwtAndSaveUserToRepo(String mail) {
        String hashedPassword = passwordEncoder.encode("super-safe-password");
        AppUser newUser = AppUser.builder()
                .mail(mail)
                .password(hashedPassword)
                .role("ADMIN")
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

    MainCategory testMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .build();

    MainCategory expectedMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .build();

    MainCategory expectedMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .build();

}