package com.pitech.services;

import com.pitech.models.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> listAllCategories();

    Optional<Category> getCategoryById(Integer categoryId);
}
