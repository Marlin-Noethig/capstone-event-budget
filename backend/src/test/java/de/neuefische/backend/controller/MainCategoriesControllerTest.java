package de.neuefische.backend.controller;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainCategoriesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MainCategoriesRepo mainCategoriesRepo;

    @BeforeEach
    public void setUp() {
        mainCategoriesRepo.deleteAll();
    }

    @Test
    void getMainCategories() {
        //GIVEN
        mainCategoriesRepo.insert(testMainCategory1);
        mainCategoriesRepo.insert(testMainCategory2);

        //WHEN
        List<MainCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/main-categories/")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MainCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory1, expectedMainCategory2);
        assertEquals(expected, actual);
    }

    MainCategory testMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .isIncome(false)
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .isIncome(true)
            .build();

    MainCategory expectedMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .isIncome(false)
            .build();

    MainCategory expectedMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .isIncome(true)
            .build();

}