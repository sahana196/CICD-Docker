package com.example.cicddocker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class StatusController {

    @Value("${APP_MESSAGE:Hello from Spring Boot}")
    private String appMessage;

    @GetMapping("health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "UP");
        body.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(body);
    }

    @GetMapping("config")
    public ResponseEntity<Map<String, Object>> config() {
        Map<String, Object> body = new HashMap<>();
        body.put("message", appMessage);
        body.put("envVarPresent", appMessage != null);
        return ResponseEntity.ok(body);
    }

}
