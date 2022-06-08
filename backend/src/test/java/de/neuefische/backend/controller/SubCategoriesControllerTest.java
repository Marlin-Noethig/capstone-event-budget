package de.neuefische.backend.controller;

import de.neuefische.backend.dto.SubCategoryDto;
import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.PositionsRepo;
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

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp() {
        subCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();
        mainCategoriesRepo.deleteAll();
        positionsRepo.deleteAll();

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

    @Test
    void postSubCategory_whenAdmin_shouldBeSuccessful() {
        //GIVEN
        //dto corresponds to testSubCategory1
        SubCategoryDto newSubCategory = SubCategoryDto.builder()
                .name("Strom")
                .mainCategoryId(testMainCategory1.getId())
                .build();

        //WHEN
        SubCategory actual = webTestClient.post()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(newSubCategory)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        assertNotNull(actual);
        assertNotNull(actual.getId());
        SubCategory expected = expectedSubCategory1;
        expected.setId(actual.getId());
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void postSubCategory_whenUser_shouldReturnClientError403() {
        //GIVEN
        //dto corresponds to testSubCategory1
        SubCategoryDto newSubCategory = SubCategoryDto.builder()
                .name("Strom")
                .mainCategoryId(testMainCategory1.getId())
                .build();

        //WHEN
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .bodyValue(newSubCategory)
                .exchange()
                //THEN
                .expectStatus().isForbidden();
    }

    @Test
    void putSubCategory_whenAdmin_shouldBeSuccessful() {
        //GIVEN
        subCategoriesRepo.insert(testSubCategory1);

        SubCategoryDto updatedSubCategory = SubCategoryDto.builder()
                .name("Wasser") //-> changed name of SubCategory
                .mainCategoryId(testMainCategory1.getId())
                .build();

        //WHEN
        SubCategory actual = webTestClient.put()
                .uri("http://localhost:" + port + "/api/sub-categories/" + testSubCategory1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(updatedSubCategory)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        SubCategory expected = expectedSubCategory1;
        expected.setName("Wasser");
        assertEquals(expected, actual);
    }

    @Test
    void putSubCategory_whenUser_shouldReturnClientError403() {
        //GIVEN
        subCategoriesRepo.insert(testSubCategory1);

        SubCategoryDto updatedSubCategory = SubCategoryDto.builder()
                .name("Wasser") //-> changed name of SubCategory
                .mainCategoryId(testMainCategory1.getId())
                .build();

        //WHEN
        webTestClient.put()
                .uri("http://localhost:" + port + "/api/sub-categories/" + testSubCategory1.getId())
                .headers(http -> http.setBearerAuth(userJwt1))
                .bodyValue(updatedSubCategory)
                .exchange()
                //THEN
                .expectStatus().isForbidden();
    }

    @Test
    void deleteSubCategoryById_whenSuccessful_shouldReturnReducedSetOfSubcategories_andPositions() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);
        positionsRepo.insert(testPosition3);

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/sub-categories/" + testSubCategory1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful();

        List<SubCategory> actualSubCategories = webTestClient.get()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SubCategory.class)
                .returnResult()
                .getResponseBody();

        List<Position> actualPositions = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<SubCategory> expectedSubCategories = List.of(expectedSubCategory2, expectedSubCategory3);
        List<Position> expectedPositions = List.of(expectedPosition2, expectedPosition3);

        assertEquals(expectedSubCategories, actualSubCategories);
        assertEquals(expectedPositions, actualPositions);
    }

    @Test
    void deleteSubCategoryById_whenUser_shouldReturnClientError403() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);
        positionsRepo.insert(testPosition3);

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/sub-categories/" + testSubCategory1.getId())
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                //THEN
                .expectStatus().isForbidden();
    }

    @Test
    void deleteSubCategoryById_whenNonExistentId_shouldReturnClientError404() {
        //GIVEN
        String wrongId = "666";

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/sub-categories/" + wrongId)
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                //THEN
                .expectStatus().isNotFound();
    }


    @Test
    void deleteSubCategoryById_whenRoleUser_shouldReturnClientError403() {
        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/sub-categories/" + testSubCategory1.getId())
                .headers(http -> http.setBearerAuth(userJwt1))
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

    Position testPosition1 = Position.builder()
            .id("1")
            .name("Starkstromanschluss")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory1.getId())
            .eventId("a")
            .build();

    Position testPosition2 = Position.builder()
            .id("2")
            .name("Lautsprecher")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory2.getId())
            .eventId("a")
            .build();

    Position testPosition3 = Position.builder()
            .id("3")
            .name("Drucker")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory3.getId())
            .eventId("b")
            .build();

    //expected Positions
    Position expectedPosition2 = Position.builder()
            .id("2")
            .name("Lautsprecher")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory2.getId())
            .eventId("a")
            .build();

    Position expectedPosition3 = Position.builder()
            .id("3")
            .name("Drucker")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory3.getId())
            .eventId("b")
            .build();

}
