package com.boltie.backend.services;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.RecordingMapper;
import com.boltie.backend.repositories.RecordingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordingService {
    private final RecordingMapper recordingMapper;
    private final RecordingRepository recordingRepository;

    public RecordingService(RecordingMapper recordingMapper,
                            RecordingRepository recordingRepository) {
        this.recordingMapper = recordingMapper;
        this.recordingRepository = recordingRepository;
    }

    public List<String> convertRecordingsToTitles(List<Recording> recordingList) {
        List<String> titles = new ArrayList<>();

        for (Recording recording : recordingList) {
            titles.add(recording.getFolderName());
        }

        return titles;
    }

    public List<RecordingDto> fetchRecordings(Long userId) {
        Optional<List<Recording>> recordingList = recordingRepository.findAllByUserId(userId);
        List<RecordingDto> recordingDtoList = new ArrayList<>();

        if(recordingList.isPresent()) {
            for (Recording recording : recordingList.get()) {
                recordingDtoList.add(recordingMapper.toRecordingDto(recording));
            }
            return recordingDtoList;
        }
        throw new AppException("No recordings found for user id: " + userId, HttpStatus.NOT_FOUND);
    }

    public RecordingDto fetchRecording(Long userId, Long recordingId) {

        if(recordingId <= 0) {
            throw new AppException("Invalid recording id: " + recordingId, HttpStatus.BAD_REQUEST);
        }

        Recording recording = recordingRepository.findNthRecordingByUserId(userId, recordingId);

        if(recording != null) {
            return recordingMapper.toRecordingDto(recording);
        }

        throw new AppException("Recording not found: " + recordingId, HttpStatus.NOT_FOUND);
    }

}
