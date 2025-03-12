package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.CategoryCommand;
import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.domain.Category;
import com.example.recipe_application.domain.Recipe;
import com.example.recipe_application.exceptions.NotFoundException;
import com.example.recipe_application.services.CategoryService;
import com.example.recipe_application.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        Optional<Recipe> recipeOpt = recipeService.findById(Long.valueOf(id));

        if (recipeOpt.isPresent()) {
            model.addAttribute("recipe", recipeOpt.get());
            log.info("Recipe details: {}", recipeOpt.get());
        } else {
            log.error("Recipe not found for ID: {}", id);
            model.addAttribute("error", "Recipe not found");
        }
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
//        model.addAttribute("recipe", new RecipeCommand());
//        return "recipe/recipeform";
        // Yeni bir RecipeCommand nesnesi oluştur
        model.addAttribute("recipe", new RecipeCommand());

        // Kategorileri veritabanından al
        List<Category> categories = categoryService.findAll();  // Kategorileri al
        model.addAttribute("categories", categories);  // Kategorileri modele ekle

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

//    @PostMapping("/recipe")
//    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
//        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
//        return "redirect:/recipe/" + savedCommand.getId() + "/show";
//    }

//    @PostMapping("/recipe")
//    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand,
//                               @RequestParam List<Long> categories) {
//
//        // Kategori ID'lerini CategoryCommand nesnelerine dönüştür
//        Set<CategoryCommand> categoryCommands = categories.stream()
//                .map(id -> new CategoryCommand())  // CategoryCommand sınıfında ID'yi alabilen bir constructor olması gerek
//                .collect(Collectors.toSet());
//
//        // RecipeCommand'a kategorileri set et
//        recipeCommand.setCategories(categoryCommands);
//
//        // RecipeCommand'ı kaydet
//        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
//
//        // Başarılı kayıttan sonra yönlendirme
//        return "redirect:/recipe/" + savedCommand.getId() + "/show";
//    }

//    @PostMapping("/recipe")
//    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand,
//                               @RequestParam List<Long> categories) {
//
//        // Kategori ID'lerini CategoryCommand nesnelerine dönüştür
//        Set<CategoryCommand> categoryCommands = categories.stream()
//                .map(CategoryCommand::new)  // ID'yi alarak CategoryCommand nesnesi oluşturuyoruz
//                .collect(Collectors.toSet());
//
//        // RecipeCommand'a kategorileri set et
//        recipeCommand.setCategories(categoryCommands);
//
//        // RecipeCommand'ı kaydet
//        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
//
//        // Başarılı kayıttan sonra yönlendirme
//        return "redirect:/recipe/" + savedCommand.getId() + "/show";
//    }


    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand,
                               BindingResult bindingResult) {

        // Eğer validation hatası varsa, formu tekrar render et
        if (bindingResult.hasErrors()) {
            System.out.println("Hata var, formu geri gönder");
            //return "recipe/form";  // Hata varsa formu geri gönderiyoruz
        }

        // Kategori ID'lerini CategoryCommand nesnelerine dönüştür
        Set<CategoryCommand> categoryCommands = recipeCommand.getCategories().stream()
                .map(category -> new CategoryCommand(category.getId()))  // Category'den id alıp CategoryCommand oluşturuyoruz
                .collect(Collectors.toSet());

        // RecipeCommand'a kategorileri set et
        recipeCommand.setCategories(categoryCommands);

        // RecipeCommand'ı kaydet
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        // Başarılı kayıttan sonra yönlendirme
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }



    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("delete id : " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception.");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
