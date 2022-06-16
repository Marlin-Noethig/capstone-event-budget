package de.neuefische.backend.service;

import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.PositionChange;
import de.neuefische.backend.repository.PositionChangesRepo;
import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PositionChangesService {
    private final PositionChangesRepo positionChangesRepo;

    @Autowired
    public PositionChangesService(PositionChangesRepo positionChangesRepo) {
        this.positionChangesRepo = positionChangesRepo;
    }

    public List<PositionChange> getPositionChangesBySubCategoryId (String subCategoryId){
        return positionChangesRepo.findAllBySubCategoryId(subCategoryId);
    }

    public void addPositionChange (Position changedPosition, AppUserInfoDto currentUser, String method){
        PositionChange newPositionChange = PositionChange.builder()
                .timestamp(LocalDateTime.now().plus(Duration.ofHours(2)))
                .method(method)
                .data(changedPosition)
                .userInfo(currentUser.getFirstName() + " " + currentUser.getLastName() + " (" + currentUser.getCompany() +")")
                .positionId(changedPosition.getId())
                .subCategoryId(changedPosition.getSubCategoryId())
                .build();

        positionChangesRepo.insert(newPositionChange);
    }
}
