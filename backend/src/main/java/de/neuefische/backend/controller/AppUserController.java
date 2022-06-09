package de.neuefische.backend.controller;

import de.neuefische.backend.security.dto.AppUserInfoDto;
import de.neuefische.backend.service.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {

    private final AppUsersService appUsersService;

    @Autowired
    public AppUserController(AppUsersService appUsersService) {
        this.appUsersService = appUsersService;
    }

    @GetMapping("current")
    public AppUserInfoDto getCurrentUser(Authentication authentication) {
        return (AppUserInfoDto) authentication.getPrincipal();
    }

    @GetMapping
    public List<AppUserInfoDto> getAllUsers(){
        return appUsersService.getAllUserInfos();
    }
}
