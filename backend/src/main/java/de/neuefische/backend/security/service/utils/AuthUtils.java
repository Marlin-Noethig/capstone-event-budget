package de.neuefische.backend.security.service.utils;

import de.neuefische.backend.security.dto.AppUserInfoDto;
import org.springframework.security.core.Authentication;

public class AuthUtils {

    private AuthUtils () {
        throw new IllegalStateException("This is a Utility Class.");
    }

    public static String getIdOfCurrentUser(Authentication authentication) {
        AppUserInfoDto currentUser =  (AppUserInfoDto) authentication.getPrincipal();
        return currentUser.getId();
    }
}
