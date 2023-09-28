package com.example.ordermanager.controller;

import com.example.ordermanager.dto.auth.register.ClientRegisterRequest;
import com.example.ordermanager.dto.auth.register.ManagerRegisterRequest;
import com.example.ordermanager.dto.auth.AuthenticationResponse;
import com.example.ordermanager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@PreAuthorize(value = "hasAuthority('ADMIN')")
@RequestMapping("register")
@RestController
public class RegisterController {

    private final AuthenticationService authenticationService;

    @PostMapping("client")
    public ResponseEntity<AuthenticationResponse> registerClient(@Valid @RequestBody ClientRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authenticationService.registerClient(request)
        );
    }

    @PostMapping("manager")
    public ResponseEntity<AuthenticationResponse> registerManager(@Valid @RequestBody ManagerRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authenticationService.registerManager(request)
        );
    }
}
