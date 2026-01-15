package com.dpetrov.imposter_companion_backend.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.GameSession;

/**
 * Repository for accessing and managing data for GameSession entities.
 */
public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {
  
  List<GameSession> findByLastActivityAtBefore(Instant cutoff);

}
