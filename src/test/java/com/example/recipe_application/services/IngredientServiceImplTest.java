package com.example.recipe_application.services;

import com.example.recipe_application.commands.IngredientCommand;
import com.example.recipe_application.converters.IngredientCommandToIngredient;
import com.example.recipe_application.converters.IngredientToIngredientCommand;
import com.example.recipe_application.domain.Ingredient;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @InjectMocks
    IngredientServiceImpl ingredientService;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository ,ingredientToIngredientCommand);
    }

//    @Test
//    void findRecipeAndRecipeIdHappyPath() throws Exception{
//        //given
//        Recipe recipe = new Recipe();
//        recipe.setId(1L);
//
//        Ingredient ingredient1 = new Ingredient();
//        ingredient1.setId(1L);
//        Ingredient ingredient2 = new Ingredient();
//        ingredient2.setId(2L);
//        Ingredient ingredient3 = new Ingredient();
//        ingredient3.setId(3L);
//
//        recipe.addIngredient(ingredient1);
//        recipe.addIngredient(ingredient2);
//        recipe.addIngredient(ingredient3);
//
//        Optional<Recipe> optionalRecipe = Optional.of(recipe);
//
//        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
//
//        //then
//        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,3L);
//
//        //when
//        Assertions.assertEquals(Long.valueOf(3L), ingredientCommand.getId());
//        Assertions.assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
//        verify(recipeRepository, times(1)).findById(anyLong());
//    }

    @Test
    void findByRecipeIdAndIngredientId() {
        // Mock data
        Recipe mockRecipe = new Recipe();
        Ingredient mockIngredient = new Ingredient();
        mockIngredient.setId(1L);
        mockRecipe.setIngredients(Set.of(mockIngredient));

        // Mock repository behavior
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));

        // Mock converter behavior
        Mockito.when(ingredientToIngredientCommand.convert(mockIngredient))
                .thenReturn(new IngredientCommand());

        // Call the method
        IngredientCommand result = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);

        // Assert the result
        assertNotNull(result, "IngredientCommand should not be null");
    }

}
