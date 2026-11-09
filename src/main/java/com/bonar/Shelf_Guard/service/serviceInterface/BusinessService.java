package com.bonar.Shelf_Guard.service.serviceInterface;

import com.bonar.Shelf_Guard.dto.request.NewBusinessRequest;
import com.bonar.Shelf_Guard.dto.response.BusinessResponse;

import java.util.UUID;


public interface BusinessService {
    BusinessResponse createBusiness(UUID ownerId, NewBusinessRequest businessRequest);
}
