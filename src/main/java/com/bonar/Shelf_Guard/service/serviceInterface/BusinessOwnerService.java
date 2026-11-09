package com.bonar.Shelf_Guard.service.serviceInterface;

import com.bonar.Shelf_Guard.dto.request.NewOwnerRequest;
import com.bonar.Shelf_Guard.entities.BusinessOwner;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BusinessOwnerService {
    BusinessOwner getReference(UUID ownerId);
}
