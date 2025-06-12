package com.crepelandia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"https://neon-entremet-b80536.netlify.app", "http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "✅ Crepelandia backend funcionando correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Crepelandia Backend API");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("details", Map.of(
            "database", "Connected",
            "api", "Running"
        ));
        return ResponseEntity.ok(response);
    }
}