package com.example.ordermanager.mapper;

import com.example.ordermanager.dto.auth.AuthenticatedUser;
import com.example.ordermanager.model.user.Manager;
import com.example.ordermanager.model.user.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testUserToAuthenticatedUser() {
        // Arrange
        User user = new Manager("testUser", "password", "Test User", "test@example.com",
                false, false, false, true);

        // Act
        AuthenticatedUser authenticatedUser = userMapper.userToAuthenticatedUser(user);

        // Assert
        assertEquals(user.getLogin(), authenticatedUser.getLogin());
        assertEquals(user.getFullName(), authenticatedUser.getFullName());
        assertEquals(user.getEmail(), authenticatedUser.getEmail());
        assertEquals(user.getRole(), authenticatedUser.getRole());
    }
}
