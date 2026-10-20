package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.dto.request.LoginRequest;
import com.bonar.Shelf_Guard.dto.request.RegisterOwnerRequest;
import com.bonar.Shelf_Guard.dto.response.ApiResponse;
import com.bonar.Shelf_Guard.dto.response.LoginResponse;
import com.bonar.Shelf_Guard.service.OwnerRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final OwnerRegistrationService ownerRegistrationService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok("Login successful", authService.login(request));
    }

    @PostMapping("/register/owner")
    public ApiResponse<LoginResponse> registerOwner(@Valid @RequestBody RegisterOwnerRequest request) {
        return ApiResponse.created("Account created", ownerRegistrationService.register(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@AuthenticationPrincipal UsersAuthDetails principal) {
        authService.logout(principal.users().getId());
        return ApiResponse.ok("Logged out", null);
    }
}
