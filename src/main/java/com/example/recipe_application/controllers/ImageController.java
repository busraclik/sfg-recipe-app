package com.example.recipe_application.controllers;

import com.example.recipe_application.commands.RecipeCommand;
import com.example.recipe_application.services.ImageService;
import com.example.recipe_application.services.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
   public String showUploadForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/"+recipeId+"/show";
    }
    @GetMapping("/recipe/{id}/recipeImage")
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (Byte wrappedByte : recipeCommand.getImage()){
            byteArray[i++] = wrappedByte;
        }

        response.setContentType("image/jpg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());

    }

}
