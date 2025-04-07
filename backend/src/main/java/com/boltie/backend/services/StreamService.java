package com.boltie.backend.services;

import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.StreamKeyDto;
import com.boltie.backend.dto.StreamTitleDto;
import com.boltie.backend.entities.Category;
import com.boltie.backend.entities.Stream;
import com.boltie.backend.entities.User;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.StreamMapper;
import com.boltie.backend.repositories.StreamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamService {

    private final OvenStreamKeyGenService ovenStreamKeyGenService;
    private final StreamRepository streamRepository;
    private final StreamMapper streamMapper;
    private final RestTemplate streamApiRestTemplate;
    private final CategoryService categoryService;

    private String API_URL;

    @Value("${oven.base.url}")
    private String BASE_URL;

    @Value("${oven.rtmp.url}")
    private String RTMP_URL;

    @Value("${oven.ws.url}")
    private String WS_URL;

    @PostConstruct
    public void init() {
        API_URL = BASE_URL + "/v1/vhosts/vhost/apps/boltie/streams";
    }

    public Stream generateDefaultStream(User user) {
        String BASE_RTMP_URL = RTMP_URL + "/boltie/" + user.getUsername();
        String BASE_WS_URL = WS_URL + "/boltie/" + user.getUsername();

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

        return Stream
                .builder()
                .title(user.getUsername() + " Stream")
                .streamKey(rtmpUrl)
                .streamUrl(wsUrl)
                .user(user)
                .category(categoryService.getDefaultCategory())
                .build();
    }


    public StreamDto getStreamDetails(String username) {
        Optional<Stream> optionalStream = streamRepository.findStreamByUser_Username(username);
        if (optionalStream.isPresent()) {
            StreamDto streamDetails = streamMapper.toStreamDto(optionalStream.get());
            streamDetails.setUsername(username);

            return streamDetails;
        }
        throw new AppException("Stream does not exist.", HttpStatus.NOT_FOUND);
    }

    public StreamKeyDto getStreamKey(String username) {
        Optional<Stream> optionalStream = streamRepository.findStreamByUser_Username(username);

        if (optionalStream.isPresent()) {
            return streamMapper.toStreamKeyDto(optionalStream.get());
        }

        throw new AppException("Unknown stream", HttpStatus.NOT_FOUND);
    }


    public StreamTitleDto editStreamTitle(String newTitle, String username) {
        Optional<Stream> optionalStream = streamRepository.findStreamByUser_Username(username);

        if (optionalStream.isPresent()) {
            Stream stream = optionalStream.get();

            stream.setTitle(newTitle);
            streamRepository.save(stream);

            return new StreamTitleDto(newTitle);
        }
        throw new AppException(String.format("User %s does not exist, cannot edit stream title", username),
                HttpStatus.BAD_REQUEST);
    }

    public void editStreamCategory(Category newCategory, String username) {
        Optional<Stream> optionalStream = streamRepository.findStreamByUser_Username(username);

        if (optionalStream.isPresent()) {
            Stream stream = optionalStream.get();

            stream.setCategory(newCategory);

            streamRepository.save(stream);
        }
    }

    public List<StreamDto> getAllStreams() {
        List<Stream> streams = streamRepository.findAll();
        List<StreamDto> streamDtoList = new ArrayList<>();

        for(Stream stream : streams) {
            StreamDto streamDto = streamMapper.toStreamDto(stream);
            streamDto.setUsername(stream.getUser().getUsername());
            streamDtoList.add(streamDto);
        }
        return streamDtoList;
    }

    public List<StreamDto> getAllLiveStreams() {
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

    public boolean userIsStreaming(String username) {
        try {
            return getLiveStreamStatus(username).getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    private ResponseEntity<String> getLiveStreamsFromApi() {
        return streamApiRestTemplate.getForEntity(API_URL, String.class);
    }
    private ResponseEntity<String> getLiveStreamStatus(String username) {
        return streamApiRestTemplate.getForEntity(String.format(API_URL + "/%s", username), String.class);
    }

    private List<String> getLiveStreamUsernames() {

        try {
            ResponseEntity<String> liveStreams = getLiveStreamsFromApi();
            String body = liveStreams.getBody();

            if(body == null) {
                return Collections.emptyList();
            }
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode responseArray = objectMapper.readTree(body).path("response");

            return objectMapper.convertValue(responseArray, new TypeReference<>(){});
        } catch (Exception e) {
            return Collections.emptyList();
        }

    }

    public List<StreamDto> getAllLiveStreamsFromCategory(String categoryUrl) {
        List<String> liveUsers = getLiveStreamUsernames();
        List<StreamDto> liveStreamsFromCategory = new ArrayList<>();

        if(!liveUsers.isEmpty()) {
            liveUsers.forEach(username -> {
               streamRepository.findStreamByUser_UsernameAndCategory_Url(username, categoryUrl).ifPresent(stream -> {
                   StreamDto streamDto = streamMapper.toStreamDto(stream);
                   streamDto.setUsername(username);
                   liveStreamsFromCategory.add(streamDto);
               });
            });
            return liveStreamsFromCategory;
        }
        return Collections.emptyList();
    }

}
