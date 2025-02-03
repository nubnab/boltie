package com.boltie.backend.services;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.mappers.RecordingMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordingService {

    private final RecordingMapper recordingMapper;

    public RecordingService(RecordingMapper recordingMapper) {
        this.recordingMapper = recordingMapper;
    }


    public List<String> convertRecordingsToTitles(List<Recording> recordingList) {
        List<String> titles = new ArrayList<>();

        for (Recording recording : recordingList) {
            RecordingDto recordingDto = recordingMapper.toRecordingDto(recording);
            titles.add(recordingDto.folderName());
        }

        return titles;
    }




}
