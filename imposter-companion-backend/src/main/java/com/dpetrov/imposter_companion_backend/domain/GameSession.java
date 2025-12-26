package com.dpetrov.imposter_companion_backend.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class GameSession {

  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  private GameStatus status;

  private Instant createdAt;

  protected GameSession() {}

  public GameSession(GameStatus status) {
    this.status = status;
    this.createdAt = Instant.now();
  }

  public UUID getId() {
    return id;
  }

  public GameStatus getStatus() {
    return status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
