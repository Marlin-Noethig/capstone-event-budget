package de.neuefische.backend.controller;

import de.neuefische.backend.model.SubCategory;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubCategoriesControllerTest {

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
    private SubCategoriesRepo subCategoriesRepo;

    @BeforeEach
    public void setUp() {
        subCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();

        jwt1 = generateJwtAndSaveUserToRepo(userMail1);
    }

    @Test
    void getSubCategories() {
        //GIVEN
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);

        //WHEN
        List<SubCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(jwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<SubCategory> expected = List.of(expectedSubCategory1, expectedSubCategory2);
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
                .bodyValue(AppUserLoginDto.builder()
                        .mail(mail)
                        .password("super-safe-password")
                        .build())
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }


    SubCategory testSubCategory1 = SubCategory.builder()
            .id("1")
            .name("Strom")
            .mainCategoryId("111")
            .build();

    SubCategory testSubCategory2 = SubCategory.builder()
            .id("2")
            .name("Veranstaltungstechnik")
            .mainCategoryId("111")
            .build();


    SubCategory expectedSubCategory1 = SubCategory.builder()
            .id("1")
            .name("Strom")
            .mainCategoryId("111")
            .build();

    SubCategory expectedSubCategory2 = SubCategory.builder()
            .id("2")
            .name("Veranstaltungstechnik")
            .mainCategoryId("111")
            .build();

}