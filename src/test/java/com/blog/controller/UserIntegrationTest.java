package com.blog.controller;

import com.blog.entity.User;
import com.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserIntegrationTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<User> mockUsers = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@example.com");
        user1.setUsername("user1");
        mockUsers.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@example.com");
        user2.setUsername("user2");
        mockUsers.add(user2);

        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act
        List<User> result = userResource.getAllUsers();

        // Assert
        assertEquals(mockUsers, result);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("user@example.com");
        mockUser.setUsername("user");

        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<User> response = userResource.getUserById(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userResource.getUserById(userId);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setUsername("newuser");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setEmail("newuser@example.com");
        createdUser.setUsername("newuser");

        when(userService.createUser(newUser)).thenReturn(createdUser);

        // Act
        User result = userResource.createUser(newUser);

        // Assert
        assertEquals(createdUser, result);
        verify(userService, times(1)).createUser(newUser);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");
        updatedUser.setUsername("updateduser");

        User mockUpdatedUser = new User();
        mockUpdatedUser.setId(userId);
        mockUpdatedUser.setEmail("updated@example.com");
        mockUpdatedUser.setUsername("updateduser");

        when(userService.updateUser(userId, updatedUser)).thenReturn(mockUpdatedUser);

        // Act
        ResponseEntity<User> response = userResource.updateUser(userId, updatedUser);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUpdatedUser, response.getBody());
        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();

        when(userService.updateUser(userId, updatedUser)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userResource.updateUser(userId, updatedUser);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void deleteUser_ShouldReturnVoidResponse() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<Void> response = userResource.deleteUser(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
    }
}
