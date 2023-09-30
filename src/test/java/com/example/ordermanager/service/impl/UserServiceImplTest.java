package com.example.ordermanager.service.impl;

import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.model.user.Admin;
import com.example.ordermanager.model.user.Client;
import com.example.ordermanager.model.user.Manager;
import com.example.ordermanager.model.user.User;
import com.example.ordermanager.repository.AdminRepository;
import com.example.ordermanager.repository.ClientRepository;
import com.example.ordermanager.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindUserById_ClientExists_ReturnsClient() {
        // Arrange
        String clientId = "1";
        Client client = new Client();
        client.setId(clientId);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        User result = underTest.findUserById(clientId);

        // Assert
        assertNotNull(result);
        assertEquals(client, result);
    }

    @Test
    void testFindUserById_ManagerExists_ReturnsManager() {
        // Arrange
        String managerId = "2";
        Manager manager = new Manager();
        manager.setId(managerId);

        when(clientRepository.findById(managerId)).thenReturn(Optional.empty());
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));

        // Act
        User result = underTest.findUserById(managerId);

        // Assert
        assertNotNull(result);
        assertEquals(manager, result);
    }

    @Test
    void testFindUserById_AdminExists_ReturnsAdmin() {
        // Arrange
        String adminId = "3";
        Admin admin = new Admin();
        admin.setId(adminId);

        when(clientRepository.findById(adminId)).thenReturn(Optional.empty());
        when(managerRepository.findById(adminId)).thenReturn(Optional.empty());
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        User result = underTest.findUserById(adminId);

        // Assert
        assertNotNull(result);
        assertEquals(admin, result);
    }

    @Test
    void testFindUserById_UserNotExists_ThrowsEntityNotExistsException() {
        // Arrange
        String userId = "4";

        when(clientRepository.findById(userId)).thenReturn(Optional.empty());
        when(managerRepository.findById(userId)).thenReturn(Optional.empty());
        when(adminRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.findUserById(userId));
    }

    @Test
    void testFindUserByLogin_ClientExists_ReturnsClient() {
        // Arrange
        String clientLogin = "clientUser";
        Client client = new Client();
        client.setLogin(clientLogin);

        when(clientRepository.findClientByLogin(clientLogin)).thenReturn(Optional.of(client));

        // Act
        User result = underTest.findUserByLogin(clientLogin);

        // Assert
        assertNotNull(result);
        assertEquals(client, result);
    }

    @Test
    void testFindUserByLogin_ManagerExists_ReturnsManager() {
        // Arrange
        String managerLogin = "managerUser";
        Manager manager = new Manager();
        manager.setLogin(managerLogin);

        when(clientRepository.findClientByLogin(managerLogin)).thenReturn(Optional.empty());
        when(managerRepository.findManagerByLogin(managerLogin)).thenReturn(Optional.of(manager));

        // Act
        User result = underTest.findUserByLogin(managerLogin);

        // Assert
        assertNotNull(result);
        assertEquals(manager, result);
    }

    @Test
    void testFindUserByLogin_AdminExists_ReturnsAdmin() {
        // Arrange
        String adminLogin = "adminUser";
        Admin admin = new Admin();
        admin.setLogin(adminLogin);

        when(clientRepository.findClientByLogin(adminLogin)).thenReturn(Optional.empty());
        when(managerRepository.findManagerByLogin(adminLogin)).thenReturn(Optional.empty());
        when(adminRepository.findAdminByLogin(adminLogin)).thenReturn(Optional.of(admin));

        // Act
        User result = underTest.findUserByLogin(adminLogin);

        // Assert
        assertNotNull(result);
        assertEquals(admin, result);
    }

    @Test
    void testFindUserByLogin_UserNotExists_ThrowsEntityNotExistsException() {
        // Arrange
        String nonExistingLogin = "nonExistingUser";

        when(clientRepository.findClientByLogin(nonExistingLogin)).thenReturn(Optional.empty());
        when(managerRepository.findManagerByLogin(nonExistingLogin)).thenReturn(Optional.empty());
        when(adminRepository.findAdminByLogin(nonExistingLogin)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotExistsException.class, () -> underTest.findUserByLogin(nonExistingLogin));
    }
}
