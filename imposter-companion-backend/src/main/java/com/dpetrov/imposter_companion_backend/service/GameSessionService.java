package com.dpetrov.imposter_companion_backend.service;

import com.dpetrov.imposter_companion_backend.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.controller.dto.GameSessionResponse;
import com.dpetrov.imposter_companion_backend.controller.dto.PlayerResponse;
import com.dpetrov.imposter_companion_backend.domain.Category;
import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.GameStatus;
import com.dpetrov.imposter_companion_backend.domain.Player;
import com.dpetrov.imposter_companion_backend.domain.PlayerRole;
import com.dpetrov.imposter_companion_backend.domain.WordPair;
import com.dpetrov.imposter_companion_backend.repository.GameSessionRepository;
import com.dpetrov.imposter_companion_backend.repository.WordPairRepository;

import jakarta.transaction.Transactional;

/**
 * Service to handle game session operations.
 */
@Service
public class GameSessionService {

  private GameSession findGameSession(UUID gameId) {
    return gameSessionRepository.findById(gameId)
      .orElseThrow(() -> new IllegalArgumentException("Game Session not found."));
  }

  private GameSessionResponse toGameSessionResponse(GameSession gameSession) {
    List<PlayerResponse> playerResponses = new ArrayList<PlayerResponse>();
    for (Player player : gameSession.getPlayers()) {
      playerResponses.add(new PlayerResponse(player.getId(), player.getName()));
    }

    return new GameSessionResponse(
        gameSession.getId(),
        gameSession.getStatus(),
        playerResponses
    );
  }

  private final GameSessionRepository gameSessionRepository;
  private final CategoryRepository categoryRepository;
  private final WordPairRepository wordPairRepository;

  public GameSessionService(GameSessionRepository gameSessionRepository, CategoryRepository categoryRepository, 
    WordPairRepository wordPairRepository) {
    this.gameSessionRepository = gameSessionRepository;
    this.categoryRepository = categoryRepository;
    this.wordPairRepository = wordPairRepository;
  }

  /**
   * Retrieves requested game session from ID.
   * 
   * @param gameId ID of game session to retrieve
   * @return retrieved game session response
   */
  public GameSessionResponse getGame(UUID gameId) {
    GameSession gameSession = findGameSession(gameId);
    return toGameSessionResponse(gameSession);
  }

  /**
   * Creates a new game session.
   * 
   * @return new created game session response
   */
  public GameSessionResponse createGame() {
    GameSession gameSession = new GameSession(GameStatus.CREATED);
    gameSessionRepository.save(gameSession);
    return toGameSessionResponse(gameSession);
  }

   /**
   * Adds a new player to an existing game session.
   * 
   * @param gameId ID of game session to join
   * @param name name of player to add
   * @return updated game session response
   */
  @Transactional
  public GameSessionResponse addPlayer(UUID gameId, String name) {
    GameSession gameSession = findGameSession(gameId);
    Player player = new Player(name, gameSession);
    gameSession.addPlayer(player);
    return toGameSessionResponse(gameSession);
  }

  /**
   * Removes a player from existing game session.
   * 
   * @param gameId ID of targeted game session
   * @param playerId ID of player to remove
   * @return updated game session response
   */
  @Transactional
  public GameSessionResponse removePlayer(UUID gameId, UUID playerId) {
    GameSession gameSession = findGameSession(gameId);
    gameSession.removePlayer(playerId);
    return toGameSessionResponse(gameSession);
  }

  /**
   * Starts requested game session if requirements met.
   * 
   * Handles assigning hint to imposter and word to normal players.
   * Fails to start if < 3 players or game session does not exist or invalid state.
   * 
   * @param gameId ID of game session to start
   * @param categoryId ID of category selected for game
   * @return started game session response
   */
  @Transactional
  public GameSessionResponse startGame(UUID gameId, UUID categoryId) {

    GameSession gameSession = findGameSession(gameId);

    if (gameSession.getStatus() != GameStatus.CREATED) { 
      throw new IllegalStateException("Invalid game session status");
    }

    List<Player> players = gameSession.getPlayers();

    if (players.size() < 3) {
      throw new IllegalStateException("Invalid amount of players");
    }

    // Reset all player roles
    for (Player player : players) {
      player.makeNormal();
    }

    // Assign imposter to random player 
    int randomPlayerIndex = ThreadLocalRandom.current().nextInt(players.size());
    Player imposter = players.get(randomPlayerIndex);
    imposter.makeImposter();

    Category category = categoryRepository.findById(categoryId)
      .orElseThrow(() -> new IllegalArgumentException("Invalid category"));
    
    // Retrieve word pairs from selected category
    List<WordPair> wordPairs = wordPairRepository.findByCategory(category);

    if (wordPairs.isEmpty()) {
      throw new IllegalStateException("No words found in category");
    }

    // Retrieve random word from selected category
    int randomWordPairIndex = ThreadLocalRandom.current().nextInt(wordPairs.size());
    WordPair wordPair = wordPairs.get(randomWordPairIndex);

    // Assign hint to imposter and word to normal players
    for (Player player : players) {
      if (player.getRole() == PlayerRole.IMPOSTER) {
        player.setSecretWord(wordPair.getImposterHint());
      } else {
        player.setSecretWord(wordPair.getWord());
      }
    }

    gameSession.setStatus(GameStatus.STARTED);
    gameSessionRepository.save(gameSession);
    return toGameSessionResponse(gameSession);
    
  }

  /**
   * Resets requested game session.
   * 
   * @param gameId ID of game session to reset
   * @return reset game session reponse
   */
  @Transactional
  public GameSessionResponse resetGame(UUID gameId) {

    GameSession gameSession = findGameSession(gameId);

    if (gameSession.getStatus() == GameStatus.CREATED) {
      throw new IllegalStateException("Invalid game session status");
    }

    List<Player> players = gameSession.getPlayers();

    for (Player player : players) {
      player.setSecretWord(null);
      player.makeNormal();
    }

    gameSession.setStatus(GameStatus.CREATED);
    gameSessionRepository.save(gameSession);
    return toGameSessionResponse(gameSession);

  }

}
