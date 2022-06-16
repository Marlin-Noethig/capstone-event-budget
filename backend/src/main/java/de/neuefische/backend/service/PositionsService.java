package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Event;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.SubCategory;
import de.neuefische.backend.repository.PositionsRepo;
import de.neuefische.backend.security.dto.AppUserInfoDto;
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
    private final EventsService eventsService;
    private final PositionChangesService positionChangesService;

    @Autowired
    public PositionsService(PositionsRepo positionsRepo, SubCategoriesService subCategoriesService, EventsService eventsService, PositionChangesService positionChangesService) {
        this.positionsRepo = positionsRepo;
        this.subCategoriesService = subCategoriesService;
        this.eventsService = eventsService;
        this.positionChangesService = positionChangesService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Position> getPositions() {
        return positionsRepo.findAll();
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<Position> getPositionsByUserId(String idOfCurrentUser) {
        List<SubCategory> subCategories = subCategoriesService.getSubCategoriesByUserId(idOfCurrentUser);
        List<Event> events = eventsService.getEventsByUserId(idOfCurrentUser);

        ArrayList<String> subCategoryIds = new ArrayList<>();
        ArrayList<String> eventIds = new ArrayList<>();

        for(SubCategory subCategory : subCategories) {
            subCategoryIds.add(subCategory.getId());
        }

        for(Event event : events){
            eventIds.add(event.getId());
        }

        return positionsRepo.findAllBySubCategoryIdIsInAndEventIdIsIn(subCategoryIds, eventIds);
    }

    public Position addNewPosition(PositionDto newPosition) {
        Position positionToAdd = new Position(newPosition);
        if (newPosition.getName() == null || newPosition.getAmount() <= 0){
            throw new IllegalArgumentException("Name of new position must be set and amount must be more than 0!");
        }
        return positionsRepo.insert(positionToAdd);
    }

    public Position updatePositionById(String id, PositionDto positionToUpdate, AppUserInfoDto currentUser) {
        if (!positionsRepo.existsById(id)) {
            throw new NoSuchElementException("Position with Id " + id +  " does not exist.");
        }
        if (positionToUpdate.getName() == null || positionToUpdate.getAmount() <= 0){
            throw new IllegalArgumentException("Name of updated position must be set and amount must be more than 0!");
        }
        Position positionToSave = new Position(positionToUpdate);
        positionToSave.setId(id);

        Position updatedPosition = positionsRepo.save(positionToSave);
        positionChangesService.addPositionChange(updatedPosition, currentUser,"UPDATE");
        return updatedPosition;
    }

    public void deletePositionById(String id) {
        if (!positionsRepo.existsById(id)) {
            throw new NoSuchElementException("Position with Id " + id +  " does not exist.");
        }

        positionsRepo.deleteById(id);

    }
}
