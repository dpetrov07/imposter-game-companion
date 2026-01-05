package com.dpetrov.imposter_companion_backend.service;

import com.dpetrov.imposter_companion_backend.repository.CategoryRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

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

  private final GameSessionRepository gameSessionRepository;
  private final CategoryRepository categoryRepository;
  private final WordPairRepository wordPairRepository;
  private final PlayerService playerService;

  public GameSessionService(GameSessionRepository gameSessionRepository, CategoryRepository categoryRepository, 
    WordPairRepository wordPairRepository, PlayerService playerService) {
    this.gameSessionRepository = gameSessionRepository;
    this.categoryRepository = categoryRepository;
    this.wordPairRepository = wordPairRepository;
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
   * Handles assigning hint to imposter and word to normal players.
   * Fails to start if < 3 players or game session does not exist or invalid state.
   * 
   * @param gameId ID of game session to start
   * @return started game session
   */
  @Transactional
  public GameSession startGame(UUID gameId, UUID categoryId) {

    GameSession gameSession = getGame(gameId);

    if (gameSession.getStatus() != GameStatus.CREATED) { 
      throw new IllegalStateException("Invalid game sessions status");
    }

    List<Player> players = playerService.getPlayersInGame(gameSession);

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

    return gameSessionRepository.save(gameSession);
    
  }

}
