package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.Business;
import com.bonar.Shelf_Guard.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Optional<Users> findByName(String businessName);
}
