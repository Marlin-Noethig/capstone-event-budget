package de.neuefische.backend.service;

import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubCategoriesServiceTest {

    private final SubCategoriesRepo subCategoriesRepo = mock(SubCategoriesRepo.class);
    private final MainCategoriesRepo mainCategoriesRepo  = mock(MainCategoriesRepo.class);
    private final SubCategoriesService subCategoriesService = new SubCategoriesService(subCategoriesRepo, mainCategoriesRepo);

    @Test
    void getSubCategories() {
        //GIVEN
        when(subCategoriesRepo.findAll()).thenReturn(List.of(testSubCategory1, testSubCategory2));

        //WHEN
        List<SubCategory> actual = subCategoriesService.getSubCategories();

        //THEN
        List<SubCategory> expected = List.of(expectedSubCategory1, expectedSubCategory2);
        verify(subCategoriesRepo).findAll();
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