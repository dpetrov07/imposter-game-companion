package com.dpetrov.imposter_companion_backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.controller.dto.PlayerSecretResponse;
import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.repository.PlayerRepository;

/**
 * Service to handle player operations.
 */
@Service
public class PlayerService {
  
  private final PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  /**
   * Adds a new player to an existing game session.
   * 
   * @param gameId ID of game session to join
   * @param request request body containing player data
   * @return new added player
   */
  public Player addPlayer(GameSession gameSession, String name) {
    Player player = new Player(name, gameSession);
    return playerRepository.save(player);
  }

  /**
   * Gets all players in an existing game session.
   * 
   * @param gameId ID of game session requested
   * @return list of all players in game
   */
  public List<Player> getPlayersInGame(GameSession gameSession) {
    return playerRepository.findByGameSession(gameSession);
  }

  /**
   * Returns payload of player role and secret word.
   * 
   * @param playerId ID of requested player
   * @return payload of player info
   */
  public PlayerSecretResponse getPlayerSecret(UUID playerId) {

    Player player = playerRepository.findById(playerId).orElseThrow();

    if (player.getGameSession().getStatus() != GameStatus.STARTED) {
      throw new IllegalStateException("Game Session has not started");
    }

    return new PlayerSecretResponse(
      player.getRole(),
      player.getSecretWord()
    );

  }

}
