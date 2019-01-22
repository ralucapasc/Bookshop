package com.pitech.services.impl;

import com.pitech.models.Category;
import com.pitech.repositories.CategoryRepository;
import com.pitech.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Iterable<Category> listAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Integer categoryId) {

        return categoryRepository.findById(categoryId);
    }


}
