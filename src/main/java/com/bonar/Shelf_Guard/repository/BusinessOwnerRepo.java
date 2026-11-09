package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessOwnerRepo extends JpaRepository<BusinessOwner, UUID> {
    void getReference(UUID ownerId);
}
