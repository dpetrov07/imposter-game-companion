package com.dpetrov.imposter_companion_backend.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;

import jakarta.transaction.Transactional;

@Service
public class GameSessionCleanupService {

  private final GameSessionRepository gameSessionRepository;

  public GameSessionCleanupService(GameSessionRepository gameSessionRepository) {
    this.gameSessionRepository = gameSessionRepository;
  }

  /**
   * Checks every 10 minutes for any unused game sessions for >45 minutes and deletes from database.
   */
  @Scheduled(fixedRate = 600000) // 10 minutes
  @Transactional
  public void cleanupInactiveGames() {

    Instant cutoff = Instant.now().minus(Duration.ofMinutes(45));
    List<GameSession> inactiveGames = gameSessionRepository.findByLastActivityAtBefore(cutoff);

    for (GameSession gameSession : inactiveGames) {
      gameSessionRepository.delete(gameSession);
    }
  }
}