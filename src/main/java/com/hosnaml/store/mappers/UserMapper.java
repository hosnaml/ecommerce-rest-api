package com.hosnaml.store.mappers;

import org.mapstruct.Mapper;
import com.hosnaml.store.entities.User;
import com.hosnaml.store.dtos.UserDto;
import org.mapstruct.Mapping;
import com.hosnaml.store.dtos.RegisterUserRequest;


@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(RegisterUserRequest userDto);
}
