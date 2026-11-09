package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.BusinessPin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessPinRepository extends JpaRepository<BusinessPin, UUID> {
    Optional<Business> findByPin(String pin);
}
