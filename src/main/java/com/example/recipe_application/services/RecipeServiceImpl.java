package com.example.recipe_application.services;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.converters.RecipeCommandToRecipe;
import com.example.recipe_application.converters.RecipeToRecipeCommand;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.exceptions.NotFoundException;
import com.example.recipe_application.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("im in the service");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        //need to check itereator
        return recipes;

    }

    @Override
    public Optional<Recipe> findById(Long l) {
      Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if (!recipeOptional.isPresent()){
            //throw new RuntimeException("not found");
            //throw custom exception
            throw new NotFoundException("Recipe Not Found For ID value:" + l.toString());
        }
        return Optional.of(recipeOptional.get());
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        Optional<Recipe> optionalRecipeCommand = findById(l);
       return recipeToRecipeCommand.convert(optionalRecipeCommand.get());
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete){
        recipeRepository.deleteById(idToDelete);
    }




}
