package com.bonar.Shelf_Guard.service.serviceInterface;

import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface BusinessPinService {
    void createPin(Users users, Business business, String pin);
}
