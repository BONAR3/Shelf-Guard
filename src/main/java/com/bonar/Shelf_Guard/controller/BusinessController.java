package com.bonar.Shelf_Guard.controller;

import com.bonar.Shelf_Guard.auth.UsersAuthDetails;
import com.bonar.Shelf_Guard.dto.request.NewBusinessRequest;
import com.bonar.Shelf_Guard.dto.response.ApiResponse;
import com.bonar.Shelf_Guard.dto.response.BusinessResponse;
import com.bonar.Shelf_Guard.service.implementation.BusinessServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessServiceImpl businessService;

    @PostMapping
    @PreAuthorize("hasROle ('OWNER')")
    public ApiResponse<BusinessResponse> createBusiness(@Valid @RequestBody NewBusinessRequest request,
                                                        @AuthenticationPrincipal UsersAuthDetails principal){
        return ApiResponse.created("Business Created",
                businessService.createBusiness(principal.users().getId(), request));
    }
}
