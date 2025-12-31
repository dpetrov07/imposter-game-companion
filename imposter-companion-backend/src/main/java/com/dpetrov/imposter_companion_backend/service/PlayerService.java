package com.dpetrov.imposter_companion_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.repository.PlayerRepository;

/**
 * Service to handle player operations.
 */
@Service
public class PlayerService {
  
  private final PlayerRepository playerRepository;
  private final GameSessionService gameSessionService;

  public PlayerService(PlayerRepository playerRepository, GameSessionService gameSessionService) {
    this.playerRepository = playerRepository;
    this.gameSessionService = gameSessionService;
  }

  /**
   * Adds a new player to an existing game session.
   * 
   * @param gameId ID of game session to join
   * @param request request body containing player data
   * @return new added player
   */
  public Player addPlayer(UUID gameId, String name) {
    GameSession gameSession = gameSessionService.getGame(gameId);
    Player player = new Player(name, gameSession);
    return playerRepository.save(player);
  }

  /**
   * Gets all players in an existing game session.
   * 
   * @param gameId ID of game session requested
   * @return list of all players in game
   */
  public List<Player> getPlayersInGame(UUID gameId) {
    GameSession gameSession = gameSessionService.getGame(gameId);
    return playerRepository.findByGameSession(gameSession);
  }

}
