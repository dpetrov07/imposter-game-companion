package com.dpetrov.imposter_companion_backend.controller.dto;

import com.dpetrov.imposter_companion_backend.domain.PlayerRole;

/**
 * Reponse DTO of JSON payload containing player's secret word or imposter hint.
 */
public record PlayerSecretResponse(
  PlayerRole playerRole,
  String secretWord
) {}

