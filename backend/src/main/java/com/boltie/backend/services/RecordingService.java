package com.boltie.backend.services;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Category;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.RecordingMapper;
import com.boltie.backend.repositories.RecordingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordingService {
    private final RecordingMapper recordingMapper;
    private final RecordingRepository recordingRepository;


    public List<String> convertRecordingsToTitles(List<Recording> recordingList) {
        List<String> titles = new ArrayList<>();

        for (Recording recording : recordingList) {
            titles.add(recording.getFolderName());
        }
        return titles;
    }

    public Recording getRecordingEntity(String username, Integer recordingTrackingId) {
        if(recordingTrackingId <= 0) {
            throw new AppException("Invalid recording id: " + recordingTrackingId, HttpStatus.BAD_REQUEST);
        }

        Optional<Recording> recording = recordingRepository
                .findRecordingByUser_UsernameAndUserRecordingTrackingId(username, recordingTrackingId);

        if(recording.isPresent()) {
            return recording.get();
        }

        throw new AppException(String
                .format("Recording %d not found for user %s", recordingTrackingId, username), HttpStatus.NOT_FOUND);
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

    public List<RecordingDto> getRecordingsByCategory(String categoryUrl) {
        Optional<List<Recording>> recordingList = recordingRepository.findAllByCategory_Url(categoryUrl);
        List<RecordingDto> recordingDtoList = new ArrayList<>();

        if(recordingList.isPresent()) {
            for (Recording recording : recordingList.get()) {
                recordingDtoList.add(recordingMapper.toRecordingDto(recording));
            }
            return recordingDtoList;
        }
        throw new AppException("No recordings found for url: " + categoryUrl, HttpStatus.NOT_FOUND);
    }

    public void editCurrentRecordingTitle(String newTitle, String username) {
        Recording currentRecording = getCurrentRecordingEntity(username);

        currentRecording.setTitle(newTitle);

        recordingRepository.save(currentRecording);
    }

    public void editCurrentRecordingCategory(Category category, String username) {
        Recording currentRecording = getCurrentRecordingEntity(username);

        currentRecording.setCategory(category);

        recordingRepository.save(currentRecording);
    }

    private Recording getCurrentRecordingEntity(String username) {
        Optional<Recording> currentRecording =
                recordingRepository.findFirstByUser_UsernameOrderByIdDesc(username);
        if(currentRecording.isPresent()) {
            return currentRecording.get();
        }
        throw new AppException(String.format("Recording for %s not found", username), HttpStatus.NOT_FOUND);
    }

    public List<RecordingDto> getAllRecordings() {
        List<Recording> recordingList = recordingRepository.findAll();
        List<RecordingDto> recordingDtoList = new ArrayList<>();

        if(!recordingList.isEmpty()) {
            for (Recording recording : recordingList) {
                recordingDtoList.add(recordingMapper.toRecordingDto(recording));
            }
            return recordingDtoList;
        }
        return recordingDtoList;
    }
}
