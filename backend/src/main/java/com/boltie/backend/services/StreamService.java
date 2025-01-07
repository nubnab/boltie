package com.boltie.backend.services;

import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.entities.Stream;
import com.boltie.backend.entities.User;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.StreamMapper;
import com.boltie.backend.repositories.StreamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamService {

    private final OvenStreamKeyGenService ovenStreamKeyGenService;
    private final StreamRepository streamRepository;
    private final StreamMapper streamMapper;
    private final RestTemplate restTemplate;

    @Value("${oven.baseurl:192.168.1.2}")
    private String BASE_URL;
    private final String API_URL = "http://" + BASE_URL + ":8081/v1/vhosts/default/apps/app/streams";

    public Stream generateDefaultStream(User user) {
        String BASE_RTMP_URL = "rtmp://" + BASE_URL + ":1935/app/" + user.getUsername();
        String BASE_WS_URL = "ws://" + BASE_URL + ":3333/app/" + user.getUsername();

        String rtmpUrl = ovenStreamKeyGenService.generate(BASE_RTMP_URL,
                Timestamp.from(ZonedDateTime.now().plusYears(2).toInstant()).getTime(),
                null,
                null,
                null);

        String wsUrl = ovenStreamKeyGenService.generate(BASE_WS_URL,
                Timestamp.from(ZonedDateTime.now().plusYears(2).toInstant()).getTime(),
                null,
                null,
                null);

        Stream stream = new Stream();
        stream.setTitle(user.getUsername() + " Stream");
        stream.setStreamKey(rtmpUrl);
        stream.setStreamUrl(wsUrl);
        stream.setUser(user);

        return stream;
    }

    public StreamDto getStreamDetails(Long id) {
        Optional<Stream> optionalStream = streamRepository.findById(id);
        if (optionalStream.isPresent()) {
            return streamMapper.toStreamDto(optionalStream.get());
        }
        throw new AppException("Unknown stream", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> editStreamTitle(Long id, String title) {
        Optional<Stream> optionalStream = streamRepository.findById(id);
        if (optionalStream.isPresent()) {
            Stream stream = optionalStream.get();
            stream.setTitle(title);
            streamRepository.save(stream);
        }

        return ResponseEntity.ok(getStreamDetails(id));
    }

    public List<StreamDto> getAllLiveStreams() throws JsonProcessingException {
        List<Stream> streams = streamRepository.findAll();
        List<String> usernames = getLiveStreamUsernames();
        List<StreamDto> liveStreams = new ArrayList<>();

        for(Stream stream : streams) {
            for(String username : usernames) {
                if(stream.getUser().getUsername().equals(username)) {
                    StreamDto streamDto = streamMapper.toStreamDto(stream);
                    streamDto.setUsername(username);
                    liveStreams.add(streamDto);
                }
            }
        }
        return liveStreams;
    }

    private ResponseEntity<String> getLiveStreamsFromApi() {
        return restTemplate.getForEntity(API_URL, String.class);
    }

    private List<String> getLiveStreamUsernames() throws JsonProcessingException {

        ResponseEntity<String> liveStreams = getLiveStreamsFromApi();

        String body = liveStreams.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode responseArray = objectMapper.readTree(body).path("response");

        return objectMapper.convertValue(responseArray, new TypeReference<>(){});

    }

}
