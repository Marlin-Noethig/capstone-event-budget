package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainCategoriesServiceTest {

    private final MainCategoriesRepo mainCategoriesRepo = mock(MainCategoriesRepo.class);
    private final MainCategoriesService mainCategoriesService = new MainCategoriesService(mainCategoriesRepo);

    @Test
    void getMainCategories() {
        //GIVEN
        when(mainCategoriesRepo.findAll()).thenReturn(List.of(testMainCategory1, testMainCategory2));

        //WHEN
        List<MainCategory> actual = mainCategoriesService.getMainCategories();

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory1, expectedMainCategory2);
        verify(mainCategoriesRepo).findAll();
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