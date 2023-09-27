package com.example.ordermanager.repository;

import com.example.ordermanager.model.user.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends UserRepository {
    Optional<Client> findClientByLogin(String login);
}
