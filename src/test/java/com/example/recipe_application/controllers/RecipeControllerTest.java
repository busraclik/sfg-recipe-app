package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.converters.RecipeCommandToRecipe;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
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
              .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
   void getUpdateView() throws Exception{
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

   }
     @Test
    void deleteAction() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService,times(1)).deleteById(anyLong());
    }



}