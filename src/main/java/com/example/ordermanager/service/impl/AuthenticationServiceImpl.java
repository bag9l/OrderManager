package com.example.ordermanager.service.impl;

import com.example.ordermanager.dto.auth.AuthenticatedUser;
import com.example.ordermanager.dto.auth.AuthenticationRequest;
import com.example.ordermanager.dto.auth.AuthenticationResponse;
import com.example.ordermanager.dto.auth.register.ClientRegisterRequest;
import com.example.ordermanager.dto.auth.register.ManagerRegisterRequest;
import com.example.ordermanager.mapper.UserMapper;
import com.example.ordermanager.model.user.Client;
import com.example.ordermanager.model.user.Manager;
import com.example.ordermanager.model.user.User;
import com.example.ordermanager.repository.ClientRepository;
import com.example.ordermanager.repository.ManagerRepository;
import com.example.ordermanager.service.AuthenticationService;
import com.example.ordermanager.service.JwtService;
import com.example.ordermanager.service.UserService;
import com.example.ordermanager.token.Token;
import com.example.ordermanager.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientRepository clientRepository;
    private final ManagerRepository managerRepository;
    private final TokenRepository tokenRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @Override
    public AuthenticationResponse registerClient(ClientRegisterRequest request) {
        User user = new Client(
                request.getLogin(),
                request.getPassword(),
                request.getFullName(),
                request.getEmail(),
                false,
                false,
                false,
                true
        );

        User savedUser = clientRepository.save(user);
        String jwt = jwtService.generateToken(user);

        saveUserToken(savedUser, jwt);

        return new AuthenticationResponse(jwt);
    }

    @Override
    public AuthenticationResponse registerManager(ManagerRegisterRequest request) {
        User user = new Manager(
                request.getLogin(),
                request.getPassword(),
                request.getFullName(),
                request.getEmail(),
                false,
                false,
                false,
                true
        );

        User savedUser = managerRepository.save(user);
        String jwt = jwtService.generateToken(user);

        saveUserToken(savedUser, jwt);

        return new AuthenticationResponse(jwt);
    }

    @Transactional
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = userService.findUserByLogin(request.getLogin());

        String jwt = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return new AuthenticationResponse(jwt);
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser(String login) {
        User user = userService.findUserByLogin(login);
        return userMapper.userToAuthenticatedUser(user);
    }


    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(jwtToken, false, false, user);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensForUser(user.getLogin());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setIsExpired(true);
            token.setIsRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
