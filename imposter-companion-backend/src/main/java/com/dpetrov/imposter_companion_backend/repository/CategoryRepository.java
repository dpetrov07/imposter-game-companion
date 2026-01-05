package com.dpetrov.imposter_companion_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.Category;

/**
 * Repository for accessing and managing data for Category entities.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  
}
