package com.boltie.backend.mappers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecordingMapper {

    RecordingDto toRecordingDto(Recording record);

}
