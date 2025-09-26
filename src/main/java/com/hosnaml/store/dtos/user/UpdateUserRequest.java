package com.hosnaml.store.dtos.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}
