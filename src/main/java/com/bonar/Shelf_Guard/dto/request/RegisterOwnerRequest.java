package com.bonar.Shelf_Guard.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Locale;

public record RegisterOwnerRequest(@NotBlank @Size(max = 100) String fullName,
                                   @NotBlank @Email String email,
                                   @NotBlank @Size(min = 8, max = 72) String password,
                                   @NotBlank @Size(max = 150) String businessName) {
    public RegisterOwnerRequest {
        fullName = fullName == null ? null : fullName.trim();
        email = email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return "RegisterOwnerRequest[email=" + email + "]";
    }
}
