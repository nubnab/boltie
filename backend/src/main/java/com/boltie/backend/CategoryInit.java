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
        put(2L, Map.entry("General", "general"));
        put(3L, Map.entry("Art", "art"));
        put(4L, Map.entry("Persona 5", "co1r76"));
        put(5L, Map.entry("Bloodborne", "co1rba"));
        put(6L, Map.entry("Portal 2", "co1rs4"));
        put(7L, Map.entry("Monster Hunter: World", "co1rst"));
        put(8L, Map.entry("Monster Hunter: Wilds", "co904o"));
        put(9L, Map.entry("Disco Elysium", "co1sfj"));
        put(10L, Map.entry("God of War (2018)", "co1tmu"));
        put(11L, Map.entry("God of War: Ragnarok", "co5s5v"));
        put(12L, Map.entry("Fallout: New Vegas", "co1u60"));
        put(13L, Map.entry("Sekiro: Shadows Die Twice", "co2a23"));
        put(14L, Map.entry("Elden Ring", "co4jni"));
        put(15L, Map.entry("Death Stranding", "co5vq8"));
        put(16L, Map.entry("Return of the Obra Dinn", "co27j9"));
        put(17L, Map.entry("Baldur's Gate 3", "co670h"));
    }};

    @Override
    public void run(String... args) throws Exception {

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
