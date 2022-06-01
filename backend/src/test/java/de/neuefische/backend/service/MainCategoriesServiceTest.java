package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
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

    @Test
    void getMainCategoriesByUserIdTest() {
        //GIVEN
        when(mainCategoriesRepo.findAllByUserIdsContaining("2")).thenReturn(List.of(testMainCategory1));
        String idOfUser = "2";

        //WHEN
        List<MainCategory> actual = mainCategoriesService.getMainCategoriesByUserId(idOfUser);

        //THEN
        List<MainCategory> expected = List.of(expectedMainCategory1);
        verify(mainCategoriesRepo).findAllByUserIdsContaining("2");
        assertEquals(expected, actual);
    }

    MainCategory testMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .userIds(new ArrayList<>(List.of("1", "2")))
            .build();

    MainCategory testMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .userIds(new ArrayList<>(List.of("1")))
            .build();

    MainCategory expectedMainCategory1 = MainCategory.builder()
            .id("1")
            .name("Production")
            .income(false)
            .userIds(new ArrayList<>(List.of("1", "2")))
            .build();

    MainCategory expectedMainCategory2 = MainCategory.builder()
            .id("2")
            .name("Incomes")
            .income(true)
            .userIds(new ArrayList<>(List.of("1")))
            .build();

}
