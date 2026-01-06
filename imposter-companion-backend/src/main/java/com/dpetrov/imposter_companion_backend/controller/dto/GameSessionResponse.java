package com.dpetrov.imposter_companion_backend.controller.dto;

import java.util.UUID;

import com.dpetrov.imposter_companion_backend.domain.GameStatus;

/**
 * Response DTO of JSON payload containing game session info.
 */
public record GameSessionResponse(
  UUID id,
  GameStatus gameStatus,
  int playerCount
 ) {}
