package com.example.ordermanager.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthenticationRequest {
    private final String login;
    private final String password;
}
