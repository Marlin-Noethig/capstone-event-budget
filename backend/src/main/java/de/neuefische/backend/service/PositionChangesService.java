package de.neuefische.backend.service;

import de.neuefische.backend.model.Position;
import de.neuefische.backend.model.PositionChange;
import de.neuefische.backend.repository.PositionChangesRepo;
import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PositionChangesService {
    private final PositionChangesRepo positionChangesRepo;

    @Autowired
    public PositionChangesService(PositionChangesRepo positionChangesRepo) {
        this.positionChangesRepo = positionChangesRepo;
    }

    public PositionChange addPositionChange (Position changedPosition, AppUserInfoDto currentUser, String method){
        PositionChange newPositionChange = PositionChange.builder()
                .timestamp(LocalDateTime.now())
                .method(method)
                .data(changedPosition)
                .fullUserName(currentUser.getFirstName() + " " + currentUser.getLastName())
                .positionId(changedPosition.getId())
                .subCategoryId(changedPosition.getSubCategoryId())
                .build();

        return positionChangesRepo.insert(newPositionChange);
    }
}
