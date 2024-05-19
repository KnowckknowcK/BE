package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MessageRedisService implements CommandLineRunner {
    private final RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        Set<String> set = redisUtil.findKeysWithPattern("*message*");
        System.out.println(set);
        for(String key:set){
            System.out.println(key);
            redisUtil.deleteDataList(key);
        }
    }
}