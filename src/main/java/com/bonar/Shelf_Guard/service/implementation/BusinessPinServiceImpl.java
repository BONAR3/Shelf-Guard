package com.bonar.Shelf_Guard.service.implementation;

import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.BusinessPin;
import com.bonar.Shelf_Guard.entities.Users;
import com.bonar.Shelf_Guard.repository.BusinessPinRepository;
import com.bonar.Shelf_Guard.service.serviceInterface.BusinessPinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class BusinessPinServiceImpl implements BusinessPinService {
    private final PasswordEncoder passwordEncoder;
    private final BusinessPinRepository businessPinRepository;

    @Override
    @Transactional
    public void createPin(Users users, Business business, String pin) {
        BusinessPin newPin = new BusinessPin();
        newPin.setUsers(users);
        newPin.setBusiness(business);
        newPin.setPin(passwordEncoder.encode(pin));
        businessPinRepository.save(newPin);

    }
}
