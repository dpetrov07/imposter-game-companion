package com.dpetrov.imposter_companion_backend.controller.dto;

import java.util.UUID;

/**
 * Response DTO of JSON payload containing category data.
 */
public record CategoryResponse(
  UUID id,
  String name
) {}
