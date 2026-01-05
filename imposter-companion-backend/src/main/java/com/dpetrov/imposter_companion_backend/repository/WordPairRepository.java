package com.dpetrov.imposter_companion_backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpetrov.imposter_companion_backend.domain.Category;
import com.dpetrov.imposter_companion_backend.domain.WordPair;
import java.util.List;


/**
 * Repository for accessing and managing data for Word Pair entities.
 */
public interface WordPairRepository extends JpaRepository<WordPair, UUID> {
  
  List<WordPair> findByCategory(Category category);

}
