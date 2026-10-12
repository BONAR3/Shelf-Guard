package com.bonar.Shelf_Guard.repository;

import com.bonar.Shelf_Guard.entities.LogInSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LogInSessionRepo extends JpaRepository<LogInSession, UUID> {

    Optional<LogInSession> findByAccountId(UUID accountId);
    void deleteByAccountId(UUID accountId);
    void deleteByExpiresAtBefore(Instant cutoff);
}
