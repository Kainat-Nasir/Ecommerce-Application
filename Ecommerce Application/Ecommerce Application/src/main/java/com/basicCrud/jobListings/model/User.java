package com.basicCrud.jobListings.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
    @Email(message = "Please provide a valid email address")

    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
