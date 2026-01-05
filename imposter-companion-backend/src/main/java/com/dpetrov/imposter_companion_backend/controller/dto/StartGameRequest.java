package com.dpetrov.imposter_companion_backend.controller.dto;

import java.util.UUID;

/**
 * Request DTO of JSON payload used to start a game session.
 */
public class StartGameRequest {
  
  private UUID categoryId;
  
  public UUID getCategoryId() {
    return categoryId;
  }

}
