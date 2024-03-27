package com.knu.KnowcKKnowcK.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping
    public String healthyCheck(){
        return "great working";
    }
}
