package de.neuefische.backend.controller;

import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubCategoriesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;


    @Autowired
    private SubCategoriesRepo subCategoriesRepo;

    @BeforeEach
    public void setUp() {
        subCategoriesRepo.deleteAll();
    }


    @Test
    void getSubCategories() {
        //GIVEN
        subCategoriesRepo.insert(testSubCategory1);
        subCategoriesRepo.insert(testSubCategory2);

        //WHEN
        List<SubCategory> actual = webTestClient.get()
                .uri("http://localhost:" + port + "/api/sub-categories/")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(SubCategory.class)
                .returnResult()
                .getResponseBody();

        //THEN
        List<SubCategory> expected = List.of(expectedSubCategory1, expectedSubCategory2);
        assertEquals(expected, actual);
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