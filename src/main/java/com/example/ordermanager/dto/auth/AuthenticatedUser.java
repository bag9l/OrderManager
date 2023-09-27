package com.example.ordermanager.dto.auth;

import com.example.ordermanager.model.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthenticatedUser {
    private final String login;
    private final String fullName;
    private final String email;
    private final Role role;
}
