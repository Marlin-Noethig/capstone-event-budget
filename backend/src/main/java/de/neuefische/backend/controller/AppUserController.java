package de.neuefische.backend.controller;


import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/")
public class AppUserController {
    @GetMapping("current")
    public String getCurrentUser(Authentication authentication) {
        AppUserInfoDto currentUser =  (AppUserInfoDto) authentication.getPrincipal();
        return currentUser.getId();
    }
}
