package com.boltie.backend.mappers;

import com.boltie.backend.dto.CategoryDto;
import com.boltie.backend.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

}
