package com.dpetrov.imposter_companion_backend.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity representing a player in a game session.
 * 
 * Players are NORMAL by default but can be assigned to IMPOSTER.
 */
@Entity
public class Player {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  private PlayerRole role;

  @ManyToOne(optional = false) // Each player belongs to only one game session
  @JoinColumn(name = "game_session_id") // Enforces in database
  private GameSession gameSession;

  protected Player() {}

  public Player(String name, GameSession gameSession) {
    this.name = name;
    this.gameSession = gameSession;
    this.role = PlayerRole.NORMAL;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public PlayerRole getRole() {
    return role;
  }

  public GameSession getGameSession() {
    return gameSession;
  }

  public void makeImposter() {
    this.role = PlayerRole.IMPOSTER;
  }
}
