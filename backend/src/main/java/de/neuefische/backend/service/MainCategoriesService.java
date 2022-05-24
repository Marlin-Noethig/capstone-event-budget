package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainCategoriesService {

    private final MainCategoriesRepo mainCategoriesRepo;

    @Autowired
    public MainCategoriesService(MainCategoriesRepo mainCategoriesRepo) {
        this.mainCategoriesRepo = mainCategoriesRepo;
    }

    public List<MainCategory> getMainCategories() {
        return mainCategoriesRepo.findAll();
    }
}
