package com.boltie.backend.controllers;

import com.boltie.backend.dto.CategoryDto;
import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.services.CategoryService;
import com.boltie.backend.services.RecordingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final RecordingService recordingService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/categories/{categoryUrl}")
    public ResponseEntity<List<RecordingDto>> getRecordingsFromCategory(@PathVariable String categoryUrl) {
        return ResponseEntity.ok(recordingService.getRecordingsByCategory(categoryUrl));
    }


}
