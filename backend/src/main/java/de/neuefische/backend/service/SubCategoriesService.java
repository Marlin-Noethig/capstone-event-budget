package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoriesService {

    private final SubCategoriesRepo subCategoriesRepo;
    private final MainCategoriesRepo mainCategoriesRepo;

    public SubCategoriesService(SubCategoriesRepo subCategoriesRepo, MainCategoriesRepo mainCategoriesRepo) {
        this.subCategoriesRepo = subCategoriesRepo;
        this.mainCategoriesRepo = mainCategoriesRepo;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubCategory> getSubCategories(){
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
}
