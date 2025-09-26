package com.hosnaml.store.dtos.user;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    public String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;
}
