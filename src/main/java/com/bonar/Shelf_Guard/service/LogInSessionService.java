package com.bonar.Shelf_Guard.service;

import com.bonar.Shelf_Guard.entities.LogInSession;
import com.bonar.Shelf_Guard.repository.LogInSessionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class LogInSessionService {

    private final LogInSessionRepo sessionRepository;
    @Value("${JWT_EXPIRATION}")
    private long sessionExpiry;

    @Transactional
    public void createSession(String sessionId, UUID accountId) {
        LogInSession session = sessionRepository.findByAccountId(accountId)
                .orElseGet(LogInSession::new);

        session.setAccountId(accountId);
        session.setActiveSessionId(sessionId);
        session.setExpiresAt(Instant.now().plus(sessionExpiry, ChronoUnit.HOURS));

        sessionRepository.save(session);
    }

    public boolean isCurrentSession(UUID accountId, String sessionId) {
        return sessionRepository.findByAccountId(accountId)
                .filter(session -> session.getActiveSessionId().equals(sessionId))
                .filter(session -> session.getExpiresAt().isAfter(Instant.now()))
                .isPresent();
    }

    @Transactional
    public void extendSession(UUID accountId, String sessionId) {
        sessionRepository.findByAccountId(accountId)
                .filter(session -> session.getActiveSessionId().equals(sessionId))
                .ifPresent(session -> {
                    session.setExpiresAt(Instant.now().plus(sessionExpiry, ChronoUnit.HOURS));
                    sessionRepository.save(session);
                });
    }

    public void invalidateSession(UUID accountId) {
        sessionRepository.deleteByAccountId(accountId);
    }

    @Scheduled(fixedRate = 300000) // runs every 5 minutes
    @Transactional
    public void purgeExpiredSessions() {
        sessionRepository.deleteByExpiresAtBefore(Instant.now());
    }

}
