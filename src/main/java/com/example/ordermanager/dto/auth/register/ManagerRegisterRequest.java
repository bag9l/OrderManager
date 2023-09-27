package com.example.ordermanager.dto.auth.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ManagerRegisterRequest {
    private final String login;
    private final String password;
    private final String fullName;
    private final String email;
}
