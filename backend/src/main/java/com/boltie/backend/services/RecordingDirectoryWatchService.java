package com.boltie.backend.services;

import com.boltie.backend.dto.UserRecordingsDto;
import com.boltie.backend.entities.Recording;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class RecordingDirectoryWatchService {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final String BASE_DIRECTORY = "/app/data";
    private final UserService userService;
    private final RecordingService recordingService;
    private final ThumbnailGeneratorService thumbnailGeneratorService;

    @PostConstruct
    public void init() throws IOException {

        Path rootDir = Path.of(Paths.get(System.getProperty("user.dir")) + BASE_DIRECTORY);

        watcherStart(rootDir);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootDir)) {
            for (Path entry : directoryStream) {
                if (Files.isDirectory(entry)) {
                    System.out.println("Starting watcher for existing directory: " + entry);
                    verifyRecordings(entry);
                    watcherStart(entry);
                }
            }
        }

    }

    public void watcherStart(Path rootDir) {
        executor.submit(() -> {
            try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
                rootDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

                WatchKey key;

                while((key = watcher.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            Path newPath = rootDir.resolve((Path) event.context());

                            if (Files.isDirectory(newPath) &&
                                    newPath.getParent().toString().equals(BASE_DIRECTORY)) {
                                System.out.println("Starting watcher for new directory: " + newPath);
                                verifyRecordings(newPath);
                                watcherStart(newPath);
                            } else if (Files.isDirectory(newPath) &&
                                    !newPath.getParent().toString().equals(BASE_DIRECTORY)) {
                                verifyRecordings(newPath.getParent());
                            }

                        }
                    }
                    key.reset();
                }

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void verifyRecordings(Path recordingDir) throws IOException {
        String[] arr = recordingDir.toString().split("/");
        String username = arr[arr.length - 1];

        UserRecordingsDto user = userService.fetchUserRecordings(username);

        List<Recording> userRecordings = user.getRecordings();

        List<String> recordingTitles = recordingService.convertRecordingsToTitles(userRecordings);
        List<String> newRecordingsFound = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(recordingDir)) {
            for (Path entry : directoryStream) {
                if(!recordingTitles.contains(entry.getFileName().toString())) {
                    newRecordingsFound.add(entry.getFileName().toString());
                    System.out.println("Found recording: " + entry.getFileName());
                    try {
                        Thread.sleep(500L); //TODO: Find a better solution to delay process
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    thumbnailGeneratorService.generateThumbnail(entry.toString(), "/thumb.jpg");
                }
            }
        }
        if(!newRecordingsFound.isEmpty()) {
            userService.addUserRecordingsToDb(username, newRecordingsFound);
        }
    }
}
