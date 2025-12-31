package com.dpetrov.imposter_companion_backend.domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Entity representing a category.
 * 
 * Tracks category name and all words in category.
 */
@Entity
public class Category {
  
  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "category") // Maps categories to associated words
  private List<WordPair> words;

  protected Category() {}

}
