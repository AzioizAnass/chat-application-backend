package com.blog.mapper;

import com.blog.dto.UserDto;
import com.blog.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-20T00:04:28+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String username = null;
        String firstName = null;
        String lastName = null;

        id = user.getId();
        email = user.getEmail();
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();

        String role = null;

        UserDto userDto = new UserDto( id, email, username, firstName, lastName, role );

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setEmail( userDto.getEmail() );
        user.setUsername( userDto.getUsername() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }
}
