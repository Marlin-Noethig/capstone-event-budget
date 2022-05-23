package de.neuefische.backend.service;


import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.SubCategoriesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoriesService {

    private final SubCategoriesRepo subCategoriesRepo;

    public SubCategoriesService(SubCategoriesRepo subCategoriesRepo) {
        this.subCategoriesRepo = subCategoriesRepo;
    }

    public List<SubCategory> getSubCategories(){
        return subCategoriesRepo.findAll();
    }
}
