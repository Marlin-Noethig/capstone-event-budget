package de.neuefische.backend.controller;

import de.neuefische.backend.model.MainCategory;
import de.neuefische.backend.security.service.utils.AuthUtils;
import de.neuefische.backend.service.MainCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main-categories/")
public class MainCategoriesController {

    private final MainCategoriesService mainCategoriesService;

    @Autowired
    public MainCategoriesController(MainCategoriesService mainCategoriesService) {
        this.mainCategoriesService = mainCategoriesService;
    }

    @GetMapping
    public List<MainCategory> getMainCategories(Authentication authentication)  {
        String roleOfCurrentUser = authentication.getAuthorities().toArray()[0].toString();

        if (roleOfCurrentUser.equals("ADMIN")){
            return mainCategoriesService.getMainCategories();
        } else {
            String idOfCurrentUser = AuthUtils.getIdOfCurrentUser(authentication);
            return mainCategoriesService.getMainCategoriesByUserId(idOfCurrentUser);
        }
    }

    @PatchMapping("{id}")
    public MainCategory updateUserIdsById(@PathVariable String id, @RequestBody List<String> userIds) {
        return mainCategoriesService.updateUserIdsById(id, userIds);
    }

    @GetMapping("balance-allowed")
    public boolean isBalanceAllowed (Authentication authentication){
        String roleOfCurrentUser = authentication.getAuthorities().toArray()[0].toString();
        if (roleOfCurrentUser.equals("ADMIN")){
            return true;
        } else {
            String idOfCurrentUser = AuthUtils.getIdOfCurrentUser(authentication);
            return mainCategoriesService.getIsBalanceAllowed(idOfCurrentUser);
        }
    }
}

