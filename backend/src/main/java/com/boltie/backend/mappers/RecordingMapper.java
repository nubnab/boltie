package com.boltie.backend.mappers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordingMapper {

    @Mapping(source = "user.username", target = "owner")
    RecordingDto toRecordingDto(Recording record);

}
