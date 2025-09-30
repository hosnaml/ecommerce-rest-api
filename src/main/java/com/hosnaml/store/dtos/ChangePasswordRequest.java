package com.hosnaml.store.dtos;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Old password is required")
    @Size(min = 6, message = "Old password must be at least 6 characters long")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password must be at least 6 characters long")
    private String newPassword;
}
