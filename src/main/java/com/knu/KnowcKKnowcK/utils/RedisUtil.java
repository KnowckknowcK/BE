package com.knu.KnowcKKnowcK.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String,String> redisTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    public void setData(String key, String value,Long expiredTime){
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    public <T> void addDataToList(String key, T data) {
        try {
            String dataJson = objectMapper.writeValueAsString(data);
            redisTemplate.opsForList().rightPush(key, dataJson);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.FAILED);
        }
    }

    public <T> List<T> getDataList(String key, Class<T> type) {
        List<String> stringList = redisTemplate.opsForList().range(key, 0, -1);
        List<T> result = new ArrayList<>();

        if (stringList != null) {
            for (String messageObject : stringList) {
                try {
                    T message = objectMapper.readValue(messageObject, type);
                    result.add(message);
                } catch (Exception e) {
                    throw new CustomException(ErrorCode.FAILED);
                }
            }
        }
        return result;
    }
}
