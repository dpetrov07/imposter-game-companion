package com.dpetrov.imposter_companion_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dpetrov.imposter_companion_backend.controller.dto.CategoryResponse;
import com.dpetrov.imposter_companion_backend.repository.CategoryRepository;

/**
 * Service to handle category operations.
 */
@Service
public class CategoryService {
  
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * Returns payload of all categories in database.
   * 
   * @return payload of categories
   */
  public List<CategoryResponse> getAllCategories() {
    return categoryRepository.findAll().stream()
      .map(cty -> new CategoryResponse(cty.getId(), cty.getName()))
      .toList();
  }
  
}
