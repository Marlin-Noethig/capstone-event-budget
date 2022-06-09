package de.neuefische.backend.security.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "appusers")
public class AppUser {

    @Id
    private String id;
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
    private String company;
    private String role;
}
