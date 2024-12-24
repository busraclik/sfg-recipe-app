package com.example.recipe_application.services;

import com.example.recipe_application.commands.IngredientCommand;
import com.example.recipe_application.converters.IngredientCommandToIngredient;
import com.example.recipe_application.converters.IngredientToIngredientCommand;
import com.example.recipe_application.domain.Ingredient;
import com.example.recipe_application.domain.Recipe;;
import com.example.recipe_application.repository.RecipeRepository;
import com.example.recipe_application.repository.UniteOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;
    private final UniteOfMeasureRepository uniteOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UniteOfMeasureRepository uniteOfMeasureRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.uniteOfMeasureRepository = uniteOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();



        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

       Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

       if (!recipeOptional.isPresent()){
           log.debug("Recipe not found");
           return new IngredientCommand();
       }else {
           Recipe recipe = recipeOptional.get();

           Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                                                           .stream()
                                                           .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                                                           .findFirst();

           if (ingredientOptional.isPresent()){
               Ingredient ingredientFound = ingredientOptional.get();

               ingredientFound.setDescription(ingredientCommand.getDescription());
               ingredientFound.setAmount(ingredientCommand.getAmount());
               ingredientFound.setUnitOfMeasure(uniteOfMeasureRepository.findById(ingredientCommand.getUom()
                                                                        .getId())
                                                                        .orElseThrow(() -> new RuntimeException("Uom not found"))); //todo address this

           }else {
               //add new Ingredient
               Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
               ingredient.setRecipe(recipe);
               recipe.addIngredient(ingredient);
           }



           Recipe savedRecipe = recipeRepository.save(recipe);

           Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                   .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                   .findFirst();

           //check by description
           if(!savedIngredientOptional.isPresent()){
               //not totally safe... But best guess
               savedIngredientOptional = savedRecipe.getIngredients().stream()
                       .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                       .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                       .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUom().getId()))
                       .findFirst();
           }

           //to do check for fail
           return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

       }
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            log.debug("no recipe found" + recipeId);
        }else{
            Recipe recipe = recipeOptional.get();
            Optional <Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (!ingredientOptional.isPresent()){
                log.error("no ingredient found");
            }else {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            }
        }

    }


}
