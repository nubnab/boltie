package com.boltie.backend.services;

import com.boltie.backend.entities.Stream;
import com.boltie.backend.entities.User;
import com.boltie.backend.repositories.StreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final OvenStreamKeyGenService ovenStreamKeyGenService;
    private final StreamRepository streamRepository;

    @Value("${oven.baseurl:192.168.1.2}")
    private String baseUrl;

    public Stream generateDefaultStream(User user) {
        return null;
    }





}
