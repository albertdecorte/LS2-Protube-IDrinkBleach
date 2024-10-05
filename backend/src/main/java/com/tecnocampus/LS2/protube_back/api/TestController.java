package com.tecnocampus.LS2.protube_back.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {

    @GetMapping("")
    public ResponseEntity<String> GetVideo(){
        return ResponseEntity.ok().body("Hello");
    }
}
