package com.dpetrov.imposter_companion_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.GameSession;

public interface GameSessionRepository extends JpaRepository<GameSession, UUID> {
  
}
