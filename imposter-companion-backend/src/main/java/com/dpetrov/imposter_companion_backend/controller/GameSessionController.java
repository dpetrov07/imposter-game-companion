package com.dpetrov.imposter_companion_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dpetrov.imposter_companion_backend.controller.dto.CreatePlayerRequest;
import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.service.GameSessionService;
import com.dpetrov.imposter_companion_backend.service.PlayerService;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST Controller for managing game sessions and players.
 *
 * Exposes end points for:
 * - Creating new game session
 * - Getting existing game
 * - Adding players to game session
 * - Getting all players in game session
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
  public GameSession getGameSession(@PathVariable UUID gameId) {
    return gameSessionService.getGame(gameId);
  }

  @PostMapping("/games")
  public GameSession createGameSession() {
    return gameSessionService.createGame();
  }

  @PostMapping("/games/{gameId}/start")
  public GameSession startGameSession(@PathVariable UUID gameId) {
    return gameSessionService.startGame(gameId);
  }
  
  
  @PostMapping("/games/{gameId}/players")
  public Player addPlayer(@PathVariable UUID gameId, @RequestBody CreatePlayerRequest request) {
    return playerService.addPlayer(gameId, request.getName());
  }

  @GetMapping("/games/{gameId}/players")
  public List<Player> getPlayers(@PathVariable UUID gameId) {
    return playerService.getPlayersInGame(gameId);
  }
  
}
