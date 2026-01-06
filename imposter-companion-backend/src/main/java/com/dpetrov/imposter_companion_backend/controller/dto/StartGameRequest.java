package com.dpetrov.imposter_companion_backend.controller.dto;

import java.util.UUID;

/**
 * Request DTO of JSON payload used to start a game session.
 */
public record StartGameRequest(
  UUID categoryId
) {}
