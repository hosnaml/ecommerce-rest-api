package com.hosnaml.store.mappers;

import org.mapstruct.Mapper;
import com.hosnaml.store.entities.User;
import com.hosnaml.store.dtos.UserDto;


@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDto toDto(User user);
}
