package com.bonar.Shelf_Guard.service;

import com.bonar.Shelf_Guard.dto.request.RegisterOwnerRequest;
import com.bonar.Shelf_Guard.dto.response.LoginResponse;

public interface OwnerRegistrationService {
    LoginResponse register(RegisterOwnerRequest request);
}
