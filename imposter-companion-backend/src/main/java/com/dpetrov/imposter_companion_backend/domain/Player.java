package com.dpetrov.imposter_companion_backend.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Player {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  private PlayerRole role;

  @ManyToOne(optional = false)
  @JoinColumn(name = "game_session_id")
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
