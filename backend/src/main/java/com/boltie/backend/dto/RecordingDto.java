package com.boltie.backend.dto;

public record RecordingDto(Integer userRecordingTrackingId,
                           String owner,
                           String title,
                           String folderName) {

}
