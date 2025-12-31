package com.dpetrov.imposter_companion_backend.controller.dto;

/**
 * Request DTO of JSON payload used to create new player in a game session.
 */
public class CreatePlayerRequest {
  
  private String name;

  public String getName() {
    return name;
  }
  
}
