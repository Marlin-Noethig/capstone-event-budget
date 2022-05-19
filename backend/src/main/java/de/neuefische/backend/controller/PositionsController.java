package de.neuefische.backend.controller;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions/")
public class PositionsController {

    private final PositionsService positionsService;

    @Autowired
    public PositionsController(PositionsService positionsService) {
        this.positionsService = positionsService;
    }

    @GetMapping
    public List<Position> getPositions(){
        return positionsService.getPositions();
    }

    @PostMapping
    public Position postPosition(@RequestBody PositionDto newPosition){
        return positionsService.addNewPosition(newPosition);
    }
}
