package de.neuefische.backend.service;

import de.neuefische.backend.dto.SubCategoryDto;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubCategoriesServiceTest {

    private final SubCategoriesRepo subCategoriesRepo = mock(SubCategoriesRepo.class);
    private final MainCategoriesRepo mainCategoriesRepo = mock(MainCategoriesRepo.class);
    private final PositionsRepo positionsRepo = mock(PositionsRepo.class);
    private final SubCategoriesService subCategoriesService = new SubCategoriesService(subCategoriesRepo, mainCategoriesRepo, positionsRepo);

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

   @Test
   void addNewSubCategory_whenNameIsNull_shouldReturnException(){
        //GIVEN
       SubCategoryDto subCategoryToAdd = SubCategoryDto.builder()
               .mainCategoryId("1")
               .build();

       //THEN
       assertThrows(IllegalArgumentException.class, () -> {
           //WHEN
           subCategoriesService.addNewSubCategory(subCategoryToAdd);
       });
   }

    @Test
    void addNewSubCategory_whenNameAlreadyExits_shouldReturnException(){
        //GIVEN
        SubCategoryDto subCategoryToAdd = SubCategoryDto.builder()
                .name("Strom")
                .mainCategoryId("1")
                .build();

        when(subCategoriesRepo.findByName("Strom")).thenReturn(testSubCategory1);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            subCategoriesService.addNewSubCategory(subCategoryToAdd);
        });
    }

    @Test
    void updateSubCategory_whenIdDoesNotExist_shouldThrowException(){
        //GIVEN
        String idOfToUpdate = "1";

        SubCategoryDto subCategoryToAdd = SubCategoryDto.builder()
                .name("Strom")
                .mainCategoryId("1")
                .build();

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            subCategoriesService.updateSubCategoryById(idOfToUpdate, subCategoryToAdd);
        });
    }


    @Test
    void updateSubCategory_whenNameIsNull_shouldThrowException(){
        //GIVEN
        String idOfToUpdate = "1";

        SubCategoryDto subCategoryToAdd = SubCategoryDto.builder()
                .mainCategoryId("1")
                .build();

        when(subCategoriesRepo.existsById(any())).thenReturn(true);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            //WHEN
            subCategoriesService.updateSubCategoryById(idOfToUpdate, subCategoryToAdd);
        });
    }


    @Test
    void deleteSubCategory_whenIdWrong_shouldThrowException() {
        //GIVEN
        String wrongIdOdToDelete = "1";
        when(subCategoriesRepo.existsById(wrongIdOdToDelete)).thenReturn(false);

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            //WHEN
            subCategoriesService.deleteSubCategoryById(wrongIdOdToDelete);
        });
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