package com.dpetrov.imposter_companion_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dpetrov.imposter_companion_backend.controller.dto.CreatePlayerRequest;
import com.dpetrov.imposter_companion_backend.controller.dto.GameSessionResponse;
import com.dpetrov.imposter_companion_backend.controller.dto.PlayerResponse;
import com.dpetrov.imposter_companion_backend.controller.dto.PlayerSecretResponse;
import com.dpetrov.imposter_companion_backend.controller.dto.StartGameRequest;
import com.dpetrov.imposter_companion_backend.service.GameSessionService;
import com.dpetrov.imposter_companion_backend.service.PlayerService;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * REST Controller for managing game sessions and players.
 *
 * Exposes end points for:
 * - Getting existing game session
 * - Creating new game session
 * - Starting game session
 * - Reseting game session
 * - Adding players to game session
 * - Getting players in game session
 */
@RestController
public class GameSessionController {

  private final GameSessionService gameSessionService;
  private final PlayerService playerService;

  public GameSessionController(GameSessionService gameSessionService, PlayerService playerService) {
    this.gameSessionService = gameSessionService;
    this.playerService = playerService;
  }

  @GetMapping("/games/{gameId}")
  public GameSessionResponse getGameSession(@PathVariable UUID gameId) {
    return gameSessionService.getGame(gameId);
  }

  @PostMapping("/games")
  public GameSessionResponse createGameSession() {
    return gameSessionService.createGame();
  }

  @PostMapping("/games/{gameId}/start")
  public GameSessionResponse startGameSession(@PathVariable UUID gameId, @RequestBody StartGameRequest request) {
    return gameSessionService.startGame(gameId, request.categoryId());
  }
  
  @PostMapping("games/{gameId}/reset")
  public GameSessionResponse resetGameSession(@PathVariable UUID gameId) {
    return gameSessionService.resetGame(gameId);
  }
  
  @PostMapping("/games/{gameId}/players")
  public PlayerResponse addPlayer(@PathVariable UUID gameId, @RequestBody CreatePlayerRequest request) {
    return gameSessionService.addPlayer(gameId, request.name());
  }

  @GetMapping("/players/{playerId}/secret")
  public PlayerSecretResponse getPlayerSecret(@PathVariable UUID playerId) {
    return playerService.getPlayerSecret(playerId);
  }
  
}
