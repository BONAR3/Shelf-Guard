package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

}
