package com.example.qchw;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

@Service
public class JsonValidationService {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonNode process(MultipartFile file, String sha256) throws Exception {

        byte[] bytes = file.getBytes();

        // validate hash
        String hash = generateSha256(bytes);

        if (!hash.equalsIgnoreCase(sha256)) 
        {
            throw new RuntimeException("SHA-256 mismatch");
        }

        // parse JSON
        JsonNode json = mapper.readTree(bytes);

        // save file
        Files.write(Path.of("uploaded.json"), bytes);

        return json;
    }

    private String generateSha256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data);

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}