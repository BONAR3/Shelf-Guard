package com.bonar.Shelf_Guard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginResponse {

    private String accessToken;
    private UUID accountId;
    private String email;
    private String fullName;
    private String accountType;
    private String role;
}
