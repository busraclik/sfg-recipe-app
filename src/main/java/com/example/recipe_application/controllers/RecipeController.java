package com.example.recipe_application.controllers;

import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        // Veritabanından gelen recipe ID'sini logla
//        log.info("Found recipe with ID: {}", id);

        // recipeService.findById() Optional döndürdüğü için onu alıyoruz
        Optional<Recipe> recipeOpt = recipeService.findById(Long.valueOf(id));

        // Eğer recipe mevcutsa
        if (recipeOpt.isPresent()) {
            // Recipe'yi modele ekle ve logla
            model.addAttribute("recipe", recipeOpt.get());
            log.info("Recipe details: {}", recipeOpt.get());
        } else {
            // Eğer recipe bulunamazsa hata mesajı ekle
            log.error("Recipe not found for ID: {}", id);
            model.addAttribute("error", "Recipe not found");
        }

        // Recipe sayfasını döndür
        return "recipe/show";
    }
}

//@Slf4j
//@Controller
//public class RecipeController {
//    private final RecipeService recipeService;
//
//    public RecipeController(RecipeService recipeService) {
//        this.recipeService = recipeService;
//    }
//
//    @RequestMapping("/recipe/show/{id}")
//    public String showById(@PathVariable String id, Model model){
//        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
//        return "recipe/show";
//    }
//
//}
