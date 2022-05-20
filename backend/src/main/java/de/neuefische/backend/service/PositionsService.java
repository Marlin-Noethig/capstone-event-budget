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

    public Position addNewPosition(PositionDto newPosition){
        Position positionToAdd = new Position();
        positionToAdd.setName(newPosition.getName());
        positionToAdd.setDescription(newPosition.getDescription());
        positionToAdd.setPrice(newPosition.getPrice());
        positionToAdd.setAmount(newPosition.getAmount());
        positionToAdd.setTax(newPosition.getTax());
        return positionsRepo.insert(positionToAdd);
    }

    public Position updatePositionById(String id, PositionDto updatedPosition){
        if(!positionsRepo.existsById(id)){
            throw new NoSuchElementException("Position with this Id does not exist.");
        }
        Position positionToSave = new Position();
        positionToSave.setName(updatedPosition.getName());
        positionToSave.setDescription(updatedPosition.getDescription());
        positionToSave.setPrice(updatedPosition.getPrice());
        positionToSave.setAmount(updatedPosition.getAmount());
        positionToSave.setTax(updatedPosition.getTax());
        return positionsRepo.save(positionToSave);
    }

    public void deletePositionById(String id){
        positionsRepo.deleteById(id);
    }
}
