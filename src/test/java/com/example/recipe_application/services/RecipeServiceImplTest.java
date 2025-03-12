package com.example.recipe_application.services;

import com.example.recipe_application.converters.RecipeCommandToRecipe;
import com.example.recipe_application.converters.RecipeToRecipeCommand;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.exceptions.NotFoundException;
import com.example.recipe_application.repository.RecipeRepository;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    RecipeCommandToRecipe recipeCommandToRecipe;
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() throws Exception{
        //setup recipedata
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(),1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Optional<Recipe> optionalRecipe = Optional.of(recipe1);
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Optional<Recipe> recipeReturned = recipeService.findById(1L);
        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


//    @Test(expected = NotFoundException.class)
//    void getRecipeByIdTestNotFound() throws Exception{
//        Optional<Recipe> recipeOptional = Optional.empty();
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//
//        //bakılacak -- ve 404 sayfası da donmuyor
//      Recipe returnedRecipe = recipeService.findById(1L);
//        //should go boom
//    }


    @Test
    void getRecipeByIdTestNotFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.findById(1L));
    }


    @Test
    void deleteById() throws Exception{
        //given
        Long idToDelete = 2L;
        //when
        recipeService.deleteById(idToDelete);
        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}