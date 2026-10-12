package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.dto.request.LoginRequest;
import com.bonar.Shelf_Guard.dto.response.LoginResponse;
import com.bonar.Shelf_Guard.entities.Staff;
import com.bonar.Shelf_Guard.entities.Users;
import com.bonar.Shelf_Guard.service.LogInSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LogInSessionService loginSessionService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        AccountAuthDetails authDetails = (AccountAuthDetails) authentication.getPrincipal();
        Users account = (Users) authDetails.users();

        String sessionId = UUID.randomUUID().toString();
        loginSessionService.createSession(sessionId, account.getId());

        String accessToken = jwtService.generateAccessToken(account, sessionId);

        String role = (account instanceof Staff staff) ? staff.getRole().getName() : null;

        return LoginResponse.builder()
                .accessToken(accessToken)
                .accountId(account.getId())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .accountType(account.getAccountType().name())
                .role(role)
                .build();
    }
}
