package com.dpetrov.imposter_companion_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.Player;

/**
 * Repository for accessing and managing data for Player entities.
 */
public interface PlayerRepository extends JpaRepository<Player, UUID> {
  
}
