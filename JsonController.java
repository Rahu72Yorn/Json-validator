package com.example.qchw;

import com.example.qchw.JsonValidationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;


@RestController
@RequestMapping("/api")
public class JsonController {

    @Autowired
    private JsonValidationService service;

    @PostMapping("/validate-jsonfile")
    public ResponseEntity<?> validate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sha256") String sha256) {

        try {
            return ResponseEntity.ok(service.process(file, sha256));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}