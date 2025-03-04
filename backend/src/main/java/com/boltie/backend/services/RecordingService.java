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

    public RecordingDto getRecording(String username, Long recordingId) {
        if(recordingId <= 0) {
            throw new AppException("Invalid recording id: " + recordingId, HttpStatus.BAD_REQUEST);
        }

        Optional<Recording> recording =
                recordingRepository.findRecordingByUser_UsernameAndId(username, recordingId);

        if(recording.isPresent()) {
            return recordingMapper.toRecordingDto(recording.get());
        }

        throw new AppException(String.format("Recording %d not found for user %s", recordingId, username), HttpStatus.NOT_FOUND);
    }

    public List<RecordingDto> getRecordings(String username) {
        Optional<List<Recording>> recordingList = recordingRepository.findAllByUser_Username(username);
        List<RecordingDto> recordingDtoList = new ArrayList<>();

        if(recordingList.isPresent()) {
            for (Recording recording : recordingList.get()) {
                recordingDtoList.add(recordingMapper.toRecordingDto(recording));
            }
            return recordingDtoList;
        }
        throw new AppException("No recordings found for username: " + username, HttpStatus.NOT_FOUND);
    }

    public void editCurrentRecordingTitle(String newTitle, String username) {
        Recording currentRecording = getCurrentRecording(username);

        currentRecording.setTitle(newTitle);

        recordingRepository.save(currentRecording);
    }

    private Recording getCurrentRecording(String username) {
        Optional<Recording> currentRecording =
                recordingRepository.findFirstByUser_UsernameOrderByIdDesc(username);
        if(currentRecording.isPresent()) {
            return currentRecording.get();
        }
        throw new AppException(String.format("Recording for %s not found", username), HttpStatus.NOT_FOUND);
    }

}
