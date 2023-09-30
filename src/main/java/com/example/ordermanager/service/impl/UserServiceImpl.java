package com.example.ordermanager.service.impl;

import com.example.ordermanager.exception.EntityNotExistsException;
import com.example.ordermanager.model.user.Admin;
import com.example.ordermanager.model.user.Client;
import com.example.ordermanager.model.user.Manager;
import com.example.ordermanager.model.user.User;
import com.example.ordermanager.repository.AdminRepository;
import com.example.ordermanager.repository.ClientRepository;
import com.example.ordermanager.repository.ManagerRepository;
import com.example.ordermanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor()
@Service
public class UserServiceImpl implements UserService {

    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;


    @Override
    public User findUserById(String id) {
        return clientRepository.findById(id).orElseGet(() ->
                managerRepository.findById(id).orElseGet(() ->
                        adminRepository.findById(id).orElseThrow(() ->
                                new EntityNotExistsException("User with id: " + id + " not found"))));
    }

    @Override
    public User findUserByLogin(String login) {
        Optional<Client> client = clientRepository.findClientByLogin(login);
        Optional<Manager> manager = managerRepository.findManagerByLogin(login);
        Optional<Admin> admin = adminRepository.findAdminByLogin(login);

        if (client.isPresent()) {
            return client.get();
        } else if (manager.isPresent()) {
            return manager.get();
        } else if (admin.isPresent()) {
            return admin.get();
        } else throw new EntityNotExistsException("User with username: " + login + " not found");
    }
}
