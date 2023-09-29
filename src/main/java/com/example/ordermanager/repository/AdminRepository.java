package com.example.ordermanager.repository;

import com.example.ordermanager.model.user.Admin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends UserRepository {
    Optional<Admin> findAdminByLogin(String login);
}
