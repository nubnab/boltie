package com.boltie.backend.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ThumbnailGeneratorService {

    public void generateThumbnail(String m3u8Path, String outputPath) {

        System.out.println("Generating thumbnail for " + m3u8Path);
        System.out.println("Output path: " + outputPath);

        String newPath = m3u8Path + "/llhls.m3u8";

        System.out.println("new path" + newPath);

        String[] testCommand = {
                "ffmpeg",
                "-i",
                newPath,
                "-ss",
                "00:00:05",
                "-vframes",
                "1",
                "-q:v",
                "2",
                m3u8Path + outputPath,
        };

        try {
            ProcessBuilder pb = new ProcessBuilder(testCommand);
            System.out.println(String.join(" ", testCommand));
            Process process = pb.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
