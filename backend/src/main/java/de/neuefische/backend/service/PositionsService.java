package de.neuefische.backend.service;

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
}
