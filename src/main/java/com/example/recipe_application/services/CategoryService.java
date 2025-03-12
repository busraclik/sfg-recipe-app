package com.example.recipe_application.services;

import com.example.recipe_application.domain.Category;

import java.util.List;

public interface CategoryService{
    List<Category> findAll();
}
