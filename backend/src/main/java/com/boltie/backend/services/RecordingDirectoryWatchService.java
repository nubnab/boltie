package com.boltie.backend.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RecordingDirectoryWatchService {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final String BASE_DIRECTORY = "/app/data/recordings";

    @PostConstruct
    public void init() throws IOException {

        Path rootDir = Path.of(Paths.get(System.getProperty("user.dir")) + BASE_DIRECTORY);

        watcherStart(rootDir);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootDir)) {
            for (Path entry : directoryStream) {
                if (Files.isDirectory(entry)) {
                    System.out.println("Starting watcher for existing directory: " + entry);
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
                            //System.out.println(newPath.getFileName());

                            if (Files.isDirectory(newPath) && newPath.getParent().toString().equals(BASE_DIRECTORY)) {
                                System.out.println("Starting watcher for new directory: " + newPath);
                                watcherStart(newPath);
                            } else if (Files.isDirectory(newPath) && !newPath.getParent().toString().equals(BASE_DIRECTORY)) {
                                System.out.println("Stream: " + newPath.getFileName());
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
}
