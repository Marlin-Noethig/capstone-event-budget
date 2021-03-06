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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainCategoriesControllerTest {

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
    private MainCategoriesRepo mainCategoriesRepo;

    @BeforeEach
    public void setUp() {
        mainCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();

        final String adminMail1 = "admin@tester.de";
        final String userMail1 = "user1@tester.de";
        final String userMail2 = "user2@tester.de";

        adminJwt1 = generateJwtAndSaveUserToRepo("a1", adminMail1, "ADMIN");
        userJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "USER");
        userJwt2 = generateJwtAndSaveUserToRepo("u2", userMail2, "USER");

    }

    @Test
    void getMainCategories_whenAdmin() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<MainCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MainCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory1, expectedMainCategory2);
        assertEquals(expected, actual);
    }

    @Test
    void getMainCategories_whenUser() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<MainCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MainCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory2);
        assertEquals(expected, actual);
    }

    @Test
    void patchUserIdsById_whenAdmin_shouldReturnUpdatedMainCategory() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);

        ArrayList<String> updatedUsers = new ArrayList<>((List.of("u1", "u2")));

        //WHEN
        MainCategory actual = webTestClient.patch()
                .uri("http://localhost:" + port + "/api/main-categories/" + testMainCategory1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(updatedUsers)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(MainCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        MainCategory expected = expectedMainCategory1;
        expected.setUserIds(updatedUsers);

        assertEquals(expected, actual);
    }

    @Test
    void patchUserIdsById_whenUser_shouldReturnClientError403() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);

        ArrayList<String> updatedUsers = new ArrayList<>((List.of("u1", "u2")));

        //WHEN
        webTestClient.patch()
                .uri("http://localhost:" + port + "/api/main-categories/" + testMainCategory1.getId())
                .headers(http -> http.setBearerAuth(userJwt1))
                .bodyValue(updatedUsers)
                .exchange()
                //Then
                .expectStatus().isForbidden();
    }

    @Test
    void patchUserIdsById_whenUserInListDoesNotExist_shouldReturnClientError() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);

        ArrayList<String> updatedUsers = new ArrayList<>((List.of("u1", "u2", "u3"))); // "u3" is not in the initiated users

        //WHEN
        webTestClient.patch()
                .uri("http://localhost:" + port + "/api/main-categories/" + testMainCategory1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(updatedUsers)
                .exchange()
                //Then
                .expectStatus().isBadRequest();
    }

    @Test
    void isBalanceAllowed_whenRoleIsAdmin_shouldReturn_true(){
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN //THEN
        Boolean.TRUE.equals(webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/balance-allowed")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(boolean.class)
                .returnResult()
                .getResponseBody());
    }

    @Test
    void isBalanceAllowed_whenRoleIsUserAndHasNotAllCategoriesVisible_shouldReturnFalse(){
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN //THEN
        Boolean.FALSE.equals(webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/balance-allowed")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(boolean.class)
                .returnResult()
                .getResponseBody());
    }

    @Test
    void isBalanceAllowed_whenRoleIsUserAndHasAllCategoriesVisible_shouldReturnTrue(){
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN //THEN
        Boolean.TRUE.equals(webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/balance-allowed")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(boolean.class)
                .returnResult()
                .getResponseBody());
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

    MainCategory testMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

    MainCategory expectedMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();

    MainCategory expectedMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

}
