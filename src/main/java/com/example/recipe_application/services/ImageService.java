package com.example.recipe_application.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long imageId, MultipartFile file);
}
