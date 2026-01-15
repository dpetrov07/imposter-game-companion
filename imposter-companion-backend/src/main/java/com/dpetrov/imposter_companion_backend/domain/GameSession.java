package com.dpetrov.imposter_companion_backend.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Entity representing a game session.
 * 
 * Assigned a status to keep track of game state.
 */
@Entity
public class GameSession {

  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  private GameStatus status;

  private Instant createdAt;

  private Instant lastActivityAt;

  @OneToMany(mappedBy = "gameSession", 
             cascade = CascadeType.ALL, 
             orphanRemoval = true
  ) // Enforce players to be owned by game session and shares lifecycle
  private List<Player> players = new ArrayList<>();

  protected GameSession() {}

  public GameSession(GameStatus status) {
    this.status = status;
    this.createdAt = Instant.now();
    this.lastActivityAt = this.createdAt;
  }

  public UUID getId() {
    return id;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getLastActivityAt() {
    return lastActivityAt;
  }

  public void touch() {
    this.lastActivityAt = Instant.now();
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void addPlayer(Player player) {
    players.add(player);
    player.setGameSession(this);
  }

  public void removePlayer(UUID playerId) {
    boolean result = players.removeIf(player -> player.getId().equals(playerId));
    if (!result) {
      throw new ResponseStatusException(
      HttpStatus.NOT_FOUND,
      "Player not found.");
    }
  }

  public void removeAllPlayers() {
    for (Player player : players) {
      player.setGameSession(null);
    }
    players.clear();
  }

  public int getPlayerCount() {
    return players.size();
  }
  
}
