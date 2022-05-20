package de.neuefische.backend.service;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.repository.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deletePositionById(String id){
        positionsRepo.deleteById(id);
    }
}
