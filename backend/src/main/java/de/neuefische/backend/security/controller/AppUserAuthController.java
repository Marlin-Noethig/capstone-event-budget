package de.neuefische.backend.security.controller;

import de.neuefische.backend.security.dto.AppUserLoginDto;
import de.neuefische.backend.security.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AppUserAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;

    @Autowired
    public AppUserAuthController(AuthenticationManager authenticationManager, JwtUtilService jwtUtilService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
    }

    @PostMapping("/login")
    public  String login(@RequestBody AppUserLoginDto appUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getMail(), appUser.getPassword()));
        return jwtUtilService.creatToken(appUser.getMail());
    }

}
