package com.bonar.Shelf_Guard.service.implementation;

import com.bonar.Shelf_Guard.dto.request.NewBusinessRequest;
import com.bonar.Shelf_Guard.dto.response.BusinessResponse;
import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.BusinessOwner;
import com.bonar.Shelf_Guard.enums.AccountStatus;
import com.bonar.Shelf_Guard.exceptions.AccountNotFoundException;
import com.bonar.Shelf_Guard.exceptions.ConflictException;
import com.bonar.Shelf_Guard.repository.BusinessOwnerRepo;
import com.bonar.Shelf_Guard.repository.BusinessRepository;
import com.bonar.Shelf_Guard.service.serviceInterface.BusinessPinService;
import com.bonar.Shelf_Guard.service.serviceInterface.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final BusinessOwnerRepo ownerRepo;
    private final BusinessPinService pinService;

    @Override
    @Transactional
    public BusinessResponse createBusiness(UUID ownerId, NewBusinessRequest businessRequest) {
        BusinessOwner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new AccountNotFoundException("Owner not found"));

        if (businessRepository.existsByBusinessName(businessRequest.name())) {
            throw new ConflictException("Business already exist");
        }

        Business business = new Business();
        business.setBusinessName(businessRequest.name());
        business.setLocation(businessRequest.location());
        business.setStatus(AccountStatus.ACTIVE);
        business.setBusinessOwner(owner);
        businessRepository.save(business);

        pinService.createPin(owner, business, businessRequest.pin());

        return new BusinessResponse(business.getBusinessName(), business.getLocation(),
                business.getStatus());
    }
}
