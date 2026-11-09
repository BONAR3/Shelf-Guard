package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.Business;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, UUID> {
    Optional<Business> findByBusinessName(String name);
    boolean existsByBusinessName(String name);
}
