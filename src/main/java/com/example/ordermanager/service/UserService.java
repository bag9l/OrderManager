package com.example.ordermanager.service;

import com.example.ordermanager.model.user.User;

public interface UserService {
    User findUserById(String id);

    User findUserByLogin(String login);
}
