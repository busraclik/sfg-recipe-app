package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.converters.RecipeCommandToRecipe;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.exceptions.NotFoundException;
import com.example.recipe_application.services.CategoryService;
import com.example.recipe_application.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    CategoryService categoryService;
    //@InjectMocks
    RecipeController recipeController;
    RecipeCommandToRecipe recipeCommandToRecipe;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(recipeService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
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
    void testGetRecipeNumberFormatException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/asdf/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }


    @Test
    void testGetRecipeNotFound() throws Exception{
       when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

       mockMvc.perform(MockMvcRequestBuilders.get("/recipe/111/show"))
               .andExpect(status().isNotFound())
               .andExpect(view().name("404error"));

        //        Recipe recipe = new Recipe();
//        recipe.setId(1L);
//        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
//
//               mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
//               .andExpect(status().isNotFound());
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