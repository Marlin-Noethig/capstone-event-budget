package de.neuefische.backend.controller;

import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.service.SubCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sub-categories")
public class SubCategoriesController {

    private final SubCategoriesService subCategoriesService;

    @Autowired
    public SubCategoriesController(SubCategoriesService subCategoriesService) {
        this.subCategoriesService = subCategoriesService;
    }

    @GetMapping
    public List<SubCategory> getSubCategories(){
        return subCategoriesService.getSubCategories();
    }
}
