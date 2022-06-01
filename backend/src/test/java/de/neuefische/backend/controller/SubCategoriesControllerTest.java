package de.neuefische.backend.controller;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
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
class SubCategoriesControllerTest {

    private String adminJwt1;
    private String userJwt1;

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SubCategoriesRepo subCategoriesRepo;

    @Autowired
    private MainCategoriesRepo mainCategoriesRepo;

    @BeforeEach
    public void setUp() {
        subCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();
        mainCategoriesRepo.deleteAll();

        final String adminMail1 = "admin@tester.de";
        final String userMail1 = "user@tester.de";

        adminJwt1 = generateJwtAndSaveUserToRepo("a1", adminMail1, "ADMIN");
        userJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "USER");
    }

    @Test
    void getSubCategories_whenAdmin_shouldReturn_all() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);

        //WHEN
        List<SubCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<SubCategory> expected = List.of(expectedSubCategory1, expectedSubCategory2, expectedSubCategory3);
        assertEquals(expected, actual);
    }

    @Test
    void getSubCategories_whenUser_shouldReturn_onlyAllowed() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);

        //WHEN
        List<SubCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<SubCategory> expected = List.of(expectedSubCategory3);
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

    MainCategory testMainCategory1 = MainCategory.builder()
            .id("111")
            .name("Production")
            .income(false)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("222")
            .name("Organisation")
            .income(false)
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

    SubCategory testSubCategory1 = SubCategory.builder()
            .id("1")
            .name("Strom")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory testSubCategory2 = SubCategory.builder()
            .id("2")
            .name("Veranstaltungstechnik")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory testSubCategory3 = SubCategory.builder()
            .id("3")
            .name("Büromittel")
            .mainCategoryId(testMainCategory2.getId())
            .build();

    SubCategory expectedSubCategory1 = SubCategory.builder()
            .id("1")
            .name("Strom")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory expectedSubCategory2 = SubCategory.builder()
            .id("2")
            .name("Veranstaltungstechnik")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory expectedSubCategory3 = SubCategory.builder()
            .id("3")
            .name("Büromittel")
            .mainCategoryId(testMainCategory2.getId())
            .build();




}