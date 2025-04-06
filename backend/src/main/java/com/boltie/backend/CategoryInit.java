package com.boltie.backend;

import com.boltie.backend.entities.Category;
import com.boltie.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryInit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final Map<Long, Map.Entry<String, String>> CATEGORY_NAMES = new LinkedHashMap<>() {{
        put(1L, Map.entry("General", "general"));
        put(2L, Map.entry("Art", "art"));
        put(3L, Map.entry("Persona 5", "persona-5"));
        put(4L, Map.entry("Bloodborne", "bloodborne"));
        put(5L, Map.entry("Portal 2", "portal-2"));
        put(6L, Map.entry("Monster Hunter: World", "monster-hunter-world"));
        put(7L, Map.entry("Monster Hunter: Wilds", "monster-hunter-wilds"));
        put(8L, Map.entry("Disco Elysium", "disco-elysium"));
        put(9L, Map.entry("God of War (2018)", "god-of-war-2018"));
        put(10L, Map.entry("God of War: Ragnarok", "god-of-war-ragnarok"));
        put(11L, Map.entry("Fallout: New Vegas", "fallout-new-vegas"));
        put(12L, Map.entry("Sekiro: Shadows Die Twice", "sekiro-shadows-die-twice"));
        put(13L, Map.entry("Elden Ring", "elden-ring"));
        put(14L, Map.entry("Death Stranding", "death-stranding"));
        put(15L, Map.entry("Return of the Obra Dinn", "return-of-the-obra-dinn"));
        put(16L, Map.entry("Baldur's Gate 3", "baldurs-gate-3"));
    }};

    @Override
    public void run(String... args) {

        if(categoryRepository.count() == 0) {
            CATEGORY_NAMES.forEach((id, entry) -> {
                Category category = Category.builder()
                        .id(id)
                        .name(entry.getKey())
                        .url(entry.getValue())
                        .streams(new ArrayList<>())
                        .recordings(new ArrayList<>())
                        .build();
                categoryRepository.save(category);
            });
        }
    }
}
