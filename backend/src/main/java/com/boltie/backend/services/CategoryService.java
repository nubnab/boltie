package com.boltie.backend.services;

import com.boltie.backend.dto.CategoryDto;
import com.boltie.backend.entities.Category;
import com.boltie.backend.mappers.CategoryMapper;
import com.boltie.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categories = new ArrayList<>();

        categoryRepository.findAll().forEach(category ->
                categories.add(categoryMapper.toCategoryDto(category)));

        return categories;
    }

    public Category getDefaultCategory() {
        return categoryRepository.findFirstById(1L);
    }

    public Optional<Category> getCategoryById(long id) {
        return categoryRepository.findById(id);
    }

}
