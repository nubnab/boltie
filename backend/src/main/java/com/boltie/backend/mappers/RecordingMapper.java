package com.boltie.backend.mappers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordingMapper {

    @Mapping(target = "owner", source = "user.username")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryUrl", source = "category.url")
    RecordingDto toRecordingDto(Recording record);

}
