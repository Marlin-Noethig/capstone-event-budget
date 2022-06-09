package de.neuefische.backend.security.dto;

import de.neuefische.backend.security.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserInfoDto {

    public AppUserInfoDto(AppUser appUser) {
        this.id = appUser.getId();
        this.mail = appUser.getMail();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.company = appUser.getCompany();
        this.role = appUser.getRole();
    }

    private String id;
    private String mail;
    private String firstName;
    private String lastName;
    private String company;
    private String role;
}
