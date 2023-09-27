package com.example.ordermanager.repository;

import com.example.ordermanager.model.user.Manager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends UserRepository {
    Optional<Manager> findManagerByLogin(String login);
}
