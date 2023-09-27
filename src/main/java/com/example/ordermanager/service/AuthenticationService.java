package com.example.ordermanager.service;

import com.example.ordermanager.dto.auth.AuthenticatedUser;
import com.example.ordermanager.dto.auth.AuthenticationRequest;
import com.example.ordermanager.dto.auth.AuthenticationResponse;
import com.example.ordermanager.dto.auth.register.ClientRegisterRequest;
import com.example.ordermanager.dto.auth.register.ManagerRegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse registerClient(ClientRegisterRequest request);

    AuthenticationResponse registerManager(ManagerRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticatedUser getAuthenticatedUser(String login);
}
