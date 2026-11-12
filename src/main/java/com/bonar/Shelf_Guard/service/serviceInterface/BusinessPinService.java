package com.bonar.Shelf_Guard.service.serviceInterface;

import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.Users;

public interface BusinessPinService {
    void createPin(Users users, Business business, String pin);
}
