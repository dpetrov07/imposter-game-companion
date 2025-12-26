package com.dpetrov.imposter_companion_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dpetrov.imposter_companion_backend.controller.dto.CreatePlayerRequest;
import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;
import com.dpetrov.imposter_companion_backend.repository.PlayerRepository;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class GameSessionController {

  private final GameSessionRepository gameSessionRepository;
  private final PlayerRepository playerRepository;

  public GameSessionController(GameSessionRepository gameSessionRepository, PlayerRepository playerRepository) {
    this.gameSessionRepository = gameSessionRepository;
    this.playerRepository = playerRepository;
  }

  @PostMapping("/games")
  public GameSession createGameSession() {
    return gameSessionRepository.save(new GameSession(GameStatus.CREATED));
  }
  

  @PostMapping("/games/{gameId}/players")
  public Player addPlayer(@PathVariable UUID gameId, @RequestBody CreatePlayerRequest request) {
    GameSession gameSession = gameSessionRepository.findById(gameId).orElseThrow();
    Player player = new Player(request.getName(), gameSession);
    return playerRepository.save(player);
  }
  
}
