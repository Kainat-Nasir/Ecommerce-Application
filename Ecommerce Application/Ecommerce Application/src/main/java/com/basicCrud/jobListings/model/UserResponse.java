package com.basicCrud.jobListings.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    @NotBlank(message = "Please provide a username")

    private String name;
    private String phone;

    @Email(message = "Please provide a valid email address")

    private String email;
}
