package de.neuefische.backend.controller;

import de.neuefische.backend.dto.PositionDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PositionsControllerTest {

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
    private PositionsRepo positionsRepo;

    @Autowired
    private SubCategoriesRepo subCategoriesRepo;

    @Autowired
    private MainCategoriesRepo mainCategoriesRepo;


    @BeforeEach
    public void setUp() {
        positionsRepo.deleteAll();
        subCategoriesRepo.deleteAll();
        mainCategoriesRepo.deleteAll();
        appUserRepository.deleteAll();

        final String adminMail1 = "admin@tester.de";
        final String userMail1 = "user@tester.de";

        adminJwt1 = generateJwtAndSaveUserToRepo("a1", adminMail1, "ADMIN");
        userJwt1 = generateJwtAndSaveUserToRepo("u1", userMail1, "USER");

    }

    @Test
    void getPositions_whenAdmin_shouldReturn_all() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);
        positionsRepo.insert(testPosition3);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition1, expectedPosition2, expectedPosition3);
        assertEquals(expected, actual);
    }

    @Test
    void getPositions_whenUser_shouldReturn_onlyAllowed() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);
        positionsRepo.insert(testPosition3);
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);
        subCategoriesRepo.insert(testSubCategory3);
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(userJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition1, expectedPosition2);
        assertEquals(expected, actual);
    }

    @Test
    void getPositions_whenWrongToken_shouldReturnForbidden() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);

        String wrongToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Indyb25nIHN0dWZmIiwiaWF0IjoxNTE2MjM5MDIyfQ._L8LHFgSbnXxLLT1Qhni-9IZsXaUG-t0Y0qU9gabqhw";

        //WHEN
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(wrongToken))
                .exchange()
                //THEN
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void postPosition() {
        //GIVEN
        //dto for testPosition1
        PositionDto newPosition = PositionDto.builder()
                .name("Starkstromanschluss")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .subCategoryId(testSubCategory1.getId())
                .build();

        //WHEN
        Position actual = webTestClient.post()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(newPosition)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        assertNotNull(actual);
        assertNotNull(actual.getId());
        Position expected = testPosition1;
        expected.setId(actual.getId());
        assertEquals(24, actual.getId().length());
        assertEquals(expected, actual);
    }

    @Test
    void postPosition_whenNameNull_shouldThrow422Status(){
        //GIVEN
        //dto for testPosition1 without name
        PositionDto newPosition = PositionDto.builder()
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(19)
                .build();

        //WHEN
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(newPosition)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void putPositionById_successful() {
        //GIVEN
        positionsRepo.insert(testPosition1);

        PositionDto updatedPosition = PositionDto.builder()
                .name("Starkstromanschluss")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7) // changed tax in update
                .subCategoryId(testSubCategory1.getId())
                .build();

        //WHEN
        Position actual = webTestClient.put()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(updatedPosition)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Position.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        Position expected = expectedPosition1;
        expected.setTax(7);
        assertEquals(expected, actual);
    }

    @Test
    void putPositionById_unsuccessful() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        String wrongId = "123";

        PositionDto updatedPosition = PositionDto.builder()
                .name("Bauzaunplane")
                .description("Lorem ipsum")
                .price(50)
                .amount(10)
                .tax(7) // changed tax in update
                .build();

        //WHEN
        webTestClient.put()
                .uri("http://localhost:" + port + "/api/positions/" + wrongId)
                .headers(http -> http.setBearerAuth(adminJwt1))
                .bodyValue(updatedPosition)
                .exchange()
                .expectStatus().isNotFound(); 

    }

    @Test
    void deletePositionById() {
        //GIVEN
        positionsRepo.insert(testPosition1);
        positionsRepo.insert(testPosition2);

        //WHEN
        webTestClient.delete()
                .uri("http://localhost:" + port + "/api/positions/" + testPosition1.getId())
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful();

        List<Position> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/positions/")
                .headers(http -> http.setBearerAuth(adminJwt1))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Position.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<Position> expected = List.of(expectedPosition2);
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
            .userIds(new ArrayList<>(List.of("u1", "u2")))
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("222")
            .name("Organisation")
            .income(false)
            .userIds(new ArrayList<>(List.of("u2")))
            .build();

    SubCategory testSubCategory1 = SubCategory.builder()
            .id("aaa")
            .name("Strom")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory testSubCategory2 = SubCategory.builder()
            .id("bbb")
            .name("Veranstaltungstechnik")
            .mainCategoryId(testMainCategory1.getId())
            .build();

    SubCategory testSubCategory3 = SubCategory.builder()
            .id("ccc")
            .name("Büromittel")
            .mainCategoryId(testMainCategory2.getId())
            .build();

    //global dummy Objects for build up / matching below
    Position testPosition1 = Position.builder()
            .id("1")
            .name("Starkstromanschluss")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory1.getId())
            .build();

    Position testPosition2 = Position.builder()
            .id("2")
            .name("Lautsprecher")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory2.getId())
            .build();

    Position testPosition3 = Position.builder()
            .id("3")
            .name("Drucker")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory3.getId())
            .build();

    //global dummy Objects for expectations / matching above
    Position expectedPosition1 = Position.builder()
            .id("1")
            .name("Starkstromanschluss")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory1.getId())
            .build();

    Position expectedPosition2 = Position.builder()
            .id("2")
            .name("Lautsprecher")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory2.getId())
            .build();

    Position expectedPosition3 = Position.builder()
            .id("3")
            .name("Drucker")
            .description("Lorem ipsum")
            .price(50)
            .amount(10)
            .tax(19)
            .subCategoryId(testSubCategory3.getId())
            .build();

}
