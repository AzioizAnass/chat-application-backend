package com.blog.mapper;

import com.blog.dto.UserDto;
import com.blog.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T23:57:57+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        String firstName = null;
        Long id = null;
        String lastName = null;
        String role = null;
        String username = null;

        email = user.getEmail();
        firstName = user.getFirstName();
        id = user.getId();
        lastName = user.getLastName();
        role = user.getRole();
        username = user.getUsername();

        UserDto userDto = new UserDto( id, email, username, firstName, lastName, role );

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDto.getEmail() );
        user.setFirstName( userDto.getFirstName() );
        user.setId( userDto.getId() );
        user.setLastName( userDto.getLastName() );
        user.setRole( userDto.getRole() );
        user.setUsername( userDto.getUsername() );

        return user;
    }
}
