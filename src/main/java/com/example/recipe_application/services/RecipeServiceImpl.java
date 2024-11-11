package com.example.recipe_application.services;

import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("im in the service");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        //need to check itereator
        //return null;
        return recipes;

    }

    @Override
    public Optional<Recipe> findById(Long l) {
        log.debug("find by id");
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if (!recipeOptional.isPresent()){
            throw new RuntimeException("not found");
        }
        return Optional.of(recipeOptional.get());
    }


}
