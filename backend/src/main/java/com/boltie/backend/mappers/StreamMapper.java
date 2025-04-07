package com.boltie.backend.mappers;

import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.StreamKeyDto;
import com.boltie.backend.entities.Stream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StreamMapper {

    @Mapping(target = "streamUrl")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryUrl", source = "category.url")
    StreamDto toStreamDto(Stream stream);

    @Mapping(target = "key", source = "streamKey")
    StreamKeyDto toStreamKeyDto(Stream stream);

}
