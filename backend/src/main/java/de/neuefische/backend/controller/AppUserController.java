package de.neuefische.backend.controller;


import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {
    @GetMapping("current")
    public AppUserInfoDto getCurrentUser(Principal principal) {
        return AppUserInfoDto.builder().mail(principal.getName()).build();
    }
}
