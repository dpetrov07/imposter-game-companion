package com.dpetrov.imposter_companion_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.GameSession;
import com.dpetrov.imposter_companion_backend.domain.Player;
import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, UUID> {

  List<Player> findByGameSession(GameSession gameSession);
  
}
