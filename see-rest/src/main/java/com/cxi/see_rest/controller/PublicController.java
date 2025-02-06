package com.cxi.see_rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PublicController{
    
    @GetMapping("/home")
    public ResponseEntity<String> getHome(){;
        return ResponseEntity.ok("Welcome to home page! This content is served from the server.");
    }
}
