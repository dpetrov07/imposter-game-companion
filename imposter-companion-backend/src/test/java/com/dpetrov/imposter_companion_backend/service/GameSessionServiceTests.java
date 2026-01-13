package com.dpetrov.imposter_companion_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dpetrov.imposter_companion_backend.controller.dto.GameSessionResponse;
import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.domain.PlayerRole;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class GameSessionServiceTests {

  @Autowired
  private GameSessionService gameSessionService;

  @Autowired
  private GameSessionRepository gameSessionRepository;

  @Autowired
  private EntityManager entityManager;

  private UUID gameId;

  private final UUID categoryId = UUID.fromString("11111111-1111-1111-1111-111111111111");

  @BeforeEach
  void setUp() {
    GameSessionResponse createdGame = gameSessionService.createGame();
    gameId = createdGame.id();

    gameSessionService.addPlayer(gameId, "Alex");
    gameSessionService.addPlayer(gameId, "Ben");
    gameSessionService.addPlayer(gameId, "Chris");
    gameSessionService.addPlayer(gameId, "Daniel");
    gameSessionService.addPlayer(gameId, "Ethan");
  }

  @Test
  void startGame() {
    GameSessionResponse startedGame = gameSessionService.startGame(gameId, categoryId);

    assertEquals(GameStatus.STARTED, startedGame.status());
    assertEquals(5, startedGame.players().size());
  }

  @Test
  void assignOneImposter() {
    gameSessionService.startGame(gameId, categoryId);
    GameSession gameSession = gameSessionRepository.findById(gameId).orElseThrow();
    long imposterCount = gameSession.getPlayers().stream()
      .filter(player -> player.getRole() == PlayerRole.IMPOSTER).count();

    assertEquals(1, imposterCount);
  }

  @Test
  void assignSecrets() {
    gameSessionService.startGame(gameId, categoryId);

    GameSession gameSession = gameSessionRepository.findById(gameId).orElseThrow();

    for (Player player : gameSession.getPlayers()) {
      assertNotNull(player.getSecretWord());
    }
  }

  @Test
  void removePlayer() {
    entityManager.flush();

    GameSession gameSession = gameSessionRepository.findById(gameId).orElseThrow();
    UUID removePlayerId = gameSession.getPlayers().get(0).getId();
    gameSessionService.removePlayer(gameId, removePlayerId);

    assertEquals(4, gameSession.getPlayerCount());
    assertTrue(gameSession.getPlayers().stream()
      .noneMatch(player -> player.getId().equals(removePlayerId)));
  }

  @Test
  void resetGame() {
    gameSessionService.startGame(gameId, categoryId);
    GameSessionResponse resetedGame = gameSessionService.resetGame(gameId);

    assertEquals(GameStatus.CREATED, resetedGame.status());

    GameSession gameSession = gameSessionRepository.findById(gameId).orElseThrow();

    for (Player player : gameSession.getPlayers()) {
      assertEquals(PlayerRole.NORMAL, player.getRole());
      assertNull(player.getSecretWord());
    }
  }
}
