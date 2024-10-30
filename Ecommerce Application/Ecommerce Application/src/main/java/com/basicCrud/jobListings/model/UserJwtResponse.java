package com.basicCrud.jobListings.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserJwtResponse {
    private Long id;
    @NotBlank(message = "Please provide a username")

    private String username;
    @Email(message = "Please provide a valid email address")

    private String email;
    private String roles;
    private String token;
}
