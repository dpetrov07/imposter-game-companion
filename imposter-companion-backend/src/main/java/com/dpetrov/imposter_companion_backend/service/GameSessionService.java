package com.dpetrov.imposter_companion_backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;

/**
 * Service to handle game session operations.
 */
@Service
public class GameSessionService {

  private final GameSessionRepository gameSessionRepository;

  public GameSessionService(GameSessionRepository gameSessionRepository) {
    this.gameSessionRepository = gameSessionRepository;
  }

  /**
   * Retrieves requested game session from Id
   * 
   * @param gameId ID of game session to retrieve
   * @return found game session
   */
  public GameSession getGame(UUID gameId) {
    return gameSessionRepository.findById(gameId).orElseThrow();
  }

  /**
   * Creates a new game session.
   * 
   * @return new created game session
   */
  public GameSession createGame() {
    return gameSessionRepository.save(new GameSession(GameStatus.CREATED));
  }

}
