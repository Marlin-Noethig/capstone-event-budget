package de.neuefische.backend.service;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.repository.MainCategoriesRepo;
import de.neuefische.backend.security.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MainCategoriesService {

    private final MainCategoriesRepo mainCategoriesRepo;
    private final AppUserRepository appUserRepository;

    @Autowired
    public MainCategoriesService(MainCategoriesRepo mainCategoriesRepo, AppUserRepository appUserRepository) {
        this.mainCategoriesRepo = mainCategoriesRepo;
        this.appUserRepository = appUserRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<MainCategory> getMainCategories() {
        return mainCategoriesRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<MainCategory> getMainCategoriesByUserId(String idOfCurrentUser) {
        return mainCategoriesRepo.findAllByUserIdsContaining(idOfCurrentUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MainCategory updateUserIdsById(String id, List<String> userIds) {

        for (String userId : userIds){
            if(!appUserRepository.existsById(userId)){
                throw new IllegalArgumentException("User with id " + userId + " does not exist.");
            }
        }

        MainCategory mainCategoryToUpdate = mainCategoriesRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Main Category with given Id " + id + " does not exist."));

        mainCategoryToUpdate.setUserIds((ArrayList<String>) userIds);

        return mainCategoriesRepo.save(mainCategoryToUpdate);
    }

    public boolean getIsBalanceAllowed(String idOfCurrentUser) {
        return mainCategoriesRepo.findAll().size() == mainCategoriesRepo.findAllByUserIdsContaining(idOfCurrentUser).size();
    }
}
