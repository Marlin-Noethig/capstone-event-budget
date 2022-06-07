package de.neuefische.backend.controller;

import de.neuefische.backend.dto.SubCategoryDto;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.security.service.utils.AuthUtils;
import de.neuefische.backend.service.SubCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-categories/")
public class SubCategoriesController {

    private final SubCategoriesService subCategoriesService;

    @Autowired
    public SubCategoriesController(SubCategoriesService subCategoriesService) {
        this.subCategoriesService = subCategoriesService;
    }

    @GetMapping
    public List<SubCategory> getSubCategories(Authentication authentication){
        String roleOfCurrentUser = authentication.getAuthorities().toArray()[0].toString();

        if (roleOfCurrentUser.equals("ADMIN")){
            return subCategoriesService.getSubCategories();
        } else {
            String idOfCurrentUser = AuthUtils.getIdOfCurrentUser(authentication);
            return subCategoriesService.getSubCategoriesByUserId(idOfCurrentUser);
        }
    }

    @PutMapping
    public SubCategory putSubCategory(@RequestBody SubCategoryDto newSubCategory) {
        return subCategoriesService.addNewSubCategory(newSubCategory);
    }

    @PutMapping("{id}")
    public SubCategory putSubCategoryById(@PathVariable String id, @RequestBody SubCategoryDto subCategoryToUpdate) {
        return subCategoriesService.updateSubCategoryById(id, subCategoryToUpdate);
    }


}
