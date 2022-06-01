package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PositionsService {
    private final PositionsRepo positionsRepo;
    private final SubCategoriesService subCategoriesService;

    @Autowired
    public PositionsService(PositionsRepo positionsRepo, SubCategoriesService subCategoriesService) {
        this.positionsRepo = positionsRepo;
        this.subCategoriesService = subCategoriesService;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Position> getPositions() {
        return positionsRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<Position> getPositionsByUserId(String idOfCurrentUser) {
        List<SubCategory> subCategories = subCategoriesService.getSubCategoriesByUserId(idOfCurrentUser);
        ArrayList<String> subCategoryIds = new ArrayList<>();

        for(SubCategory subCategory : subCategories) {
            subCategoryIds.add(subCategory.getId());
        }

        return positionsRepo.findAllBySubCategoryIdIsIn(subCategoryIds);
    }

    public Position addNewPosition(PositionDto newPosition) {
        Position positionToAdd = new Position(newPosition);
        if (newPosition.getName() == null || newPosition.getAmount() <= 0){
            throw new IllegalArgumentException("Name of new position must be set and amount must be more than 0!");
        }
        return positionsRepo.insert(positionToAdd);
    }

    public Position updatePositionById(String id, PositionDto updatedPosition) {
        if (!positionsRepo.existsById(id)) {
            throw new NoSuchElementException("Position with Id " + id +  " does not exist.");
        }
        if (updatedPosition.getName() == null || updatedPosition.getAmount() <= 0){
            throw new IllegalArgumentException("Name of updated position must be set and amount must be more than 0!");
        }
        Position positionToSave = new Position(updatedPosition);
        positionToSave.setId(id);
        return positionsRepo.save(positionToSave);
    }

    public void deletePositionById(String id) {
        if (!positionsRepo.existsById(id)) {
            throw new NoSuchElementException("Position with Id " + id +  " does not exist.");
        }

        positionsRepo.deleteById(id);

    }


}
