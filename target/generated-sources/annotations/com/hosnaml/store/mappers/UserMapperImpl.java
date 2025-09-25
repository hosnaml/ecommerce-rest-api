package com.hosnaml.store.mappers;

import com.hosnaml.store.dtos.RegisterUserRequest;
import com.hosnaml.store.dtos.UserDto;
import com.hosnaml.store.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-25T18:51:34+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserDto userDto = new UserDto( id, name, email );

        return userDto;
    }

    @Override
    public User toEntity(RegisterUserRequest userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( userDto.getName() );
        user.email( userDto.getEmail() );
        user.password( userDto.getPassword() );

        return user.build();
    }
}
