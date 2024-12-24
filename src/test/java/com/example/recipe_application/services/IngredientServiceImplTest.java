package com.example.recipe_application.services;

import com.example.recipe_application.commands.IngredientCommand;
import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.converters.IngredientCommandToIngredient;
import com.example.recipe_application.converters.IngredientToIngredientCommand;
import com.example.recipe_application.domain.Ingredient;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.repository.RecipeRepository;
import com.example.recipe_application.repository.UniteOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.parsers.SAXParser;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.*;


public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UniteOfMeasureRepository uniteOfMeasureRepository;
    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    IngredientCommandToIngredient ingredientCommandToIngredient;
    @InjectMocks
    IngredientServiceImpl ingredientService;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository , uniteOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
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
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));

        // Mock converter behavior
        when(ingredientToIngredientCommand.convert(mockIngredient))
                .thenReturn(new IngredientCommand());

        // Call the method
        IngredientCommand result = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);

        // Assert the result
        assertNotNull(result, "IngredientCommand should not be null");
    }


//    @Test
//    void saveRecipeCommand() throws Exception{
//        IngredientCommand ingredientCommand = new IngredientCommand();
//        ingredientCommand.setId(3L);
//        ingredientCommand.setRecipeId(2L);
//
//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
//
//        Recipe savedRecipe = new Recipe();
//        savedRecipe.addIngredient(new Ingredient());
//        savedRecipe.getIngredients().iterator().next().setId(3L);
//
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//        when(recipeRepository.save(any())).thenReturn(savedRecipe);
//        when(ingredientCommandToIngredient.convert(ingredientCommand))
//                .thenReturn(new Ingredient());
//
//
//        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
//
//        assertEquals(Long.valueOf(3L), savedCommand.getId());
//        verify(recipeRepository, times(1)).findById(anyLong());
//        verify(recipeRepository, times(1)).save(any(Recipe.class));
//
//    }


    //todo saveIngredient test

//    @Test
//    void saveIngredientCommandTest() throws Exception{
//        //given
//        IngredientCommand command = new IngredientCommand();
//        command.setId(3L);
//        command.setRecipeId(2L);
//
//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
//
//        Recipe savedRecipe = new Recipe();
//        savedRecipe.addIngredient(new Ingredient());
//        savedRecipe.getIngredients().iterator().next().setId(3L);
//
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//        when(recipeRepository.save(any())).thenReturn(savedRecipe);
//
//        //when
//        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
//
//        //then
//        assertEquals(Long.valueOf(3L), savedCommand.getId());
//        verify(recipeRepository, times(1)).findById(anyLong());
//        verify(recipeRepository, times(1)).save(any(Recipe.class));
//    }

    @Test
    void deleteById() throws Exception{

        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }




    }
