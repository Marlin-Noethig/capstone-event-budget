package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainCategoriesService {

    private final MainCategoriesRepo mainCategoriesRepo;

    @Autowired
    public MainCategoriesService(MainCategoriesRepo mainCategoriesRepo) {
        this.mainCategoriesRepo = mainCategoriesRepo;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<MainCategory> getMainCategories() {
        return mainCategoriesRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<MainCategory> getMainCategoriesByUserId(String idOfCurrentUser) {
        return mainCategoriesRepo.findAllByUserIdsContaining(idOfCurrentUser);
    }
}
