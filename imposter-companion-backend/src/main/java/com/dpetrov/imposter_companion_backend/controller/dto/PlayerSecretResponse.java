package com.dpetrov.imposter_companion_backend.controller.dto;

import com.dpetrov.imposter_companion_backend.domain.PlayerRole;

/**
 * Request DTO of JSON payload containing player's secret word or imposter hint.
 */
public class PlayerSecretResponse {
  
  private PlayerRole playerRole;
  private String secretWord;

  public PlayerSecretResponse(PlayerRole playerRole, String secretWord) {
    this.playerRole = playerRole;
    this.secretWord = secretWord;
  }

  public PlayerRole getPlayerRole() {
    return playerRole;
  }

  public String getSecretWord() {
    return secretWord;
  }
}
