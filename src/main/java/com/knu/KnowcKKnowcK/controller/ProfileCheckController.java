package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.config.RedisConfig;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileCheckController {
    private final RedisUtil redisUtil;
    @Value("${spring.profiles.name}")
    private String profile;
    @GetMapping("profile")
    public String profileCheck(){
        return profile+" profile";
    }

    @GetMapping("redis")
    public String redis(){
        redisUtil.setData("key","안녕안녕",60*60*1000L);
        return redisUtil.getData("key");
    }
}
