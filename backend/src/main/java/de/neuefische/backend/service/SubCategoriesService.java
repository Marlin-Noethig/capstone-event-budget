package de.neuefische.backend.service;

import de.neuefische.backend.dto.SubCategoryDto;
import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubCategoriesService {

    private final SubCategoriesRepo subCategoriesRepo;
    private final MainCategoriesRepo mainCategoriesRepo;
    private final PositionsRepo positionsRepo;

    public SubCategoriesService(SubCategoriesRepo subCategoriesRepo, MainCategoriesRepo mainCategoriesRepo, PositionsRepo positionsRepo) {
        this.subCategoriesRepo = subCategoriesRepo;
        this.mainCategoriesRepo = mainCategoriesRepo;
        this.positionsRepo = positionsRepo;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubCategory> getSubCategories() {
        return subCategoriesRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<SubCategory> getSubCategoriesByUserId(String idOfCurrentUser) {
        List<MainCategory> mainCategories = mainCategoriesRepo.findAllByUserIdsContaining(idOfCurrentUser);
        ArrayList<String> mainCategoryIds = new ArrayList<>();

        for (MainCategory mainCategory : mainCategories) {
            mainCategoryIds.add(mainCategory.getId());
        }

        return subCategoriesRepo.findAllByMainCategoryIdIsIn(mainCategoryIds);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SubCategory addNewSubCategory(SubCategoryDto subCategoryToAdd) {

        if (subCategoriesRepo.findByName(subCategoryToAdd.getName()) != null) {
            throw new IllegalArgumentException("Subcategory with name " + subCategoryToAdd.getName() + " already exists.");
        }
        if (subCategoryToAdd.getName() == null) {
            throw new IllegalArgumentException("Name of new Subcategory can not be empty.");
        }

        SubCategory newSubCategory = new SubCategory(subCategoryToAdd);
        return subCategoriesRepo.insert(newSubCategory);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public SubCategory updateSubCategoryById(String id, SubCategoryDto subCategoryToUpdate) {
        if (!subCategoriesRepo.existsById(id)) {
            throw new NoSuchElementException("No Subcategory with Id " + id + " found to be updated.");
        }
        if (subCategoryToUpdate.getName() == null) {
            throw new IllegalArgumentException("Name of new Subcategory can not be empty.");
        }

        SubCategory updatedSubCategory = new SubCategory(subCategoryToUpdate);
        updatedSubCategory.setId(id);

        return subCategoriesRepo.save(updatedSubCategory);
    }

    public void deleteSubCategoryById(String id) {
        if (!subCategoriesRepo.existsById(id)){
            throw new NoSuchElementException("Subcategory with Id " + id + " does not exist.");
        }
        subCategoriesRepo.deleteById(id);
        positionsRepo.deleteAllBySubCategoryId(id);
    }
}
