package com.dpetrov.imposter_companion_backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;

import jakarta.transaction.Transactional;

/**
 * Service to handle game session operations.
 */
@Service
public class GameSessionService {

  private final GameSessionRepository gameSessionRepository;
  private final PlayerService playerService;

  public GameSessionService(GameSessionRepository gameSessionRepository, PlayerService playerService) {
    this.gameSessionRepository = gameSessionRepository;
    this.playerService = playerService;
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

  /**
   * Starts requested game session if requirements met.
   * 
   * Fails to start if < 3 players or game session does not exist or invalid state
   * 
   * @param gameId ID of game session to start
   * @return started game session
   */
  @Transactional
  public GameSession startGame(UUID gameId) {

    GameSession gameSession = getGame(gameId);

    if (gameSession.getStatus() != GameStatus.CREATED) { 
      throw new IllegalStateException("Invalid game sessions status");
    }

    if (playerService.getPlayersInGame(gameId).size() < 3) {
      throw new IllegalStateException("Invalid amount of players");
    }

    gameSession.setStatus(GameStatus.STARTED);

    return gameSessionRepository.save(gameSession);
    
  }

}
