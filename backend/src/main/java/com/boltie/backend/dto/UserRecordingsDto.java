package com.boltie.backend.dto;

import com.boltie.backend.entities.Recording;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecordingsDto {

    private String username;
    private List<Recording> recordings;

}
