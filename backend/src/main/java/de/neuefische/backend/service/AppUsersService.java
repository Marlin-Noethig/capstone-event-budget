package de.neuefische.backend.service;

import de.neuefische.backend.security.dto.AppUserInfoDto;
import de.neuefische.backend.security.model.AppUser;
import de.neuefische.backend.security.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUsersService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUsersService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppUserInfoDto> getAllUserInfos() {
        List<AppUser> allUsers = appUserRepository.findAll();
        List<AppUserInfoDto> allUserInfos = new ArrayList<>();

        for (AppUser user : allUsers){
            AppUserInfoDto appUserInfoDto = new AppUserInfoDto(user);
            allUserInfos.add(appUserInfoDto);
        }

        return allUserInfos;
    }
}
