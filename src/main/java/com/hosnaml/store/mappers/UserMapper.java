package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.user.UpdateUserRequest;
import org.mapstruct.Mapper;
import com.hosnaml.store.entities.User;
import com.hosnaml.store.dtos.user.UserDto;
import com.hosnaml.store.dtos.user.RegisterUserRequest;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(RegisterUserRequest userDto);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
