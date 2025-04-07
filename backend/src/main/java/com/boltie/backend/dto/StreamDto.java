package com.boltie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamDto {

    private Long id;
    private String username;
    private String title;
    private String categoryName;
    private String categoryUrl;
    private String streamUrl;

}
