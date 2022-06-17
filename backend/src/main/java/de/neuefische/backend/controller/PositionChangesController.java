package de.neuefische.backend.controller;


import de.neuefische.backend.model.PositionChange;
import de.neuefische.backend.service.PositionChangesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/position-changes")
public class PositionChangesController {

    private final PositionChangesService positionChangesService;

    @Autowired
    public PositionChangesController(PositionChangesService positionChangesService) {
        this.positionChangesService = positionChangesService;
    }

    @GetMapping
    public List<PositionChange> getPositionChangesBySubCategory (@RequestParam String subCategoryId){
        return positionChangesService.getPositionChangesBySubCategoryId(subCategoryId);
    }
}
