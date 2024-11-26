package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.converters.RecipeCommandToRecipe;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    //@InjectMocks
    RecipeController recipeController;
    RecipeCommandToRecipe recipeCommandToRecipe;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void getRecipePage() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        //MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }

    @Test
    void getNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void postNewRecipeForm() throws Exception{
      RecipeCommand recipeCommand = new RecipeCommand();
      recipeCommand.setId(2L);

      when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

      mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .param("id","")
              .param("description", "some string")
      )
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/recipe/show/2"));
    }



}