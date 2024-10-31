package com.example.recipe_application.services;

import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long l);
}
