package com.hosnaml.store.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.validation.constraints.*;

@AllArgsConstructor
@Getter
public class UserDto {
    @JsonIgnore
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
