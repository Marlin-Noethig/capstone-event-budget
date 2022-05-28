package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PositionsService {
    private final PositionsRepo positionsRepo;

    @Autowired
    public PositionsService(PositionsRepo positionsRepo) {
        this.positionsRepo = positionsRepo;
    }


    public List<Position> getPositions() {
        return positionsRepo.findAll();
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
        Position positionToSave = new Position(updatedPosition);
        positionToSave.setId(id);
        return positionsRepo.save(positionToSave);
    }

    public void deletePositionById(String id) {
        positionsRepo.deleteById(id);
    }
}
