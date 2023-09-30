package com.example.ordermanager.dto.auth.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
public class ClientRegisterRequest {
    @Size(min = 6, max = 20, message = "login shouldn't be shorter than 6 and longer than 20")
    @NotBlank(message = "login shouldn't be empty")
    private final String login;

    @Size(min = 8, max = 20, message = "password shouldn't be shorter than 8 and longer than 20")
    @NotBlank(message = "password shouldn't be empty")
    private final String password;

    @Size(min = 10, max = 100, message = "full name shouldn't be shorter than 10 and longer than 100")
    @NotBlank(message = "full name shouldn't be empty")
    private final String fullName;

    @Email(message = "email is not valid")
    private final String email;
}
