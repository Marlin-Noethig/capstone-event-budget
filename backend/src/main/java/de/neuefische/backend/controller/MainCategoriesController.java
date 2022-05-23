package de.neuefische.backend.controller;


import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.service.MainCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main-categories")
public class MainCategoriesController {

    private final MainCategoriesService mainCategoriesService;

    @Autowired
    public MainCategoriesController(MainCategoriesService mainCategoriesService) {
        this.mainCategoriesService = mainCategoriesService;
    }

    @GetMapping
    public List<MainCategory> getMainCategories(){
        return mainCategoriesService.getMainCategories();
    }

}
