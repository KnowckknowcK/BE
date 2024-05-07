package com.knu.KnowcKKnowcK.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileCheckController {
    @Value("${spring.profiles.name}")
    private String profile;
    @GetMapping("profile")
    public String profileCheck(){
        return profile+" profile";
    }
}
