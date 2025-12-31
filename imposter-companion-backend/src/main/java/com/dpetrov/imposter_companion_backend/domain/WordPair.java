package com.dpetrov.imposter_companion_backend.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity representing a word pair.
 * 
 * Holds word name and assosicated imposter hint and category.
 */
@Entity
public class WordPair {
  
  @Id
  @GeneratedValue
  private UUID id;

  private String name;
  private String imposterHint;

  @ManyToOne(optional = false) // Each word belongs to only one category
  @JoinColumn(nullable = false) // Enforces in database
  private Category category;

  protected WordPair() {}

}
