package com.dpetrov.imposter_companion_backend.controller.dto;

import java.util.UUID;

/**
 * Response DTO of JSON payload containing player info.
 */
public record PlayerResponse(
  UUID id,
  String name
) {}
