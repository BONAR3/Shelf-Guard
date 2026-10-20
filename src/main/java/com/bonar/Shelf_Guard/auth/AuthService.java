package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.dto.request.LoginRequest;
import com.bonar.Shelf_Guard.dto.request.RegisterOwnerRequest;
import com.bonar.Shelf_Guard.dto.response.LoginResponse;
import com.bonar.Shelf_Guard.entities.BusinessOwner;
import com.bonar.Shelf_Guard.entities.Staff;
import com.bonar.Shelf_Guard.entities.Users;
import com.bonar.Shelf_Guard.enums.AccountStatus;
import com.bonar.Shelf_Guard.enums.AccountType;
import com.bonar.Shelf_Guard.exceptions.ConflictException;
import com.bonar.Shelf_Guard.repository.AccountRepository;
import com.bonar.Shelf_Guard.service.LogInSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LogInSessionService loginSessionService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UsersAuthDetails authDetails = (UsersAuthDetails) authentication.getPrincipal();
        Users account = (Users) authDetails.users();

        return issueLogin(authDetails.users());
    }

    @Transactional
    public LoginResponse register(RegisterOwnerRequest request) {
        if (accountRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use");
        }

        BusinessOwner owner = new BusinessOwner();
        owner.setFullName(request.fullName());
        owner.setEmail(request.email());
        owner.setPassword(passwordEncoder.encode(request.password()));
        owner.setAccountType(AccountType.OWNER);
        owner.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(owner);

        return issueLogin(owner);
    }

    private LoginResponse issueLogin(Users account) {
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

    public void logout(UUID accountId) {
        loginSessionService.invalidateSession(accountId);
    }
}
