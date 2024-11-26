package com.example.recipe_application.services;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();
    Optional<Recipe> findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

}
