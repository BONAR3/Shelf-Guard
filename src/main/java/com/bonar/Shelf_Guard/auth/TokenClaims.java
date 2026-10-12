package com.bonar.Shelf_Guard.auth;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class TokenClaims {

    private String email;
    private String accountId;
    private String sessionId;
    private String accountType;
    private String role;
//    private Set<Roles> roles;

}
