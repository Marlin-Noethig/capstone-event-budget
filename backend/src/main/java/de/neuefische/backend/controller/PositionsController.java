package de.neuefische.backend.controller;

import de.neuefische.backend.dto.PositionDto;
import de.neuefische.backend.model.Position;
import de.neuefische.backend.security.dto.AppUserInfoDto;
import de.neuefische.backend.security.service.utils.AuthUtils;
import de.neuefische.backend.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public List<Position> getPositions(Authentication authentication){
        String roleOfCurrentUser = authentication.getAuthorities().toArray()[0].toString();
        if (roleOfCurrentUser.equals("ADMIN")){
            return positionsService.getPositions();
        } else {
            String idOfCurrentUser = AuthUtils.getIdOfCurrentUser(authentication);
            return positionsService.getPositionsByUserId(idOfCurrentUser);
        }
    }

    @PostMapping
    public Position postPosition(@RequestBody PositionDto newPosition, Authentication authentication){
        AppUserInfoDto currentUser = (AppUserInfoDto) authentication.getPrincipal();
        return positionsService.addNewPosition(newPosition, currentUser);
    }

    @PutMapping("{id}")
    public Position putPositionById(@PathVariable String id, @RequestBody PositionDto updatedPosition, Authentication authentication){
        AppUserInfoDto currentUser = (AppUserInfoDto) authentication.getPrincipal();
        return positionsService.updatePositionById(id, updatedPosition, currentUser);
    }

    @DeleteMapping("{id}")
    public void deletePositionById(@PathVariable String id, Authentication authentication){
        AppUserInfoDto currentUser = (AppUserInfoDto) authentication.getPrincipal();
        positionsService.deletePositionById(id, currentUser);
    }
}
