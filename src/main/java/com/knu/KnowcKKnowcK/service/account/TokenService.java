package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.utils.JwtUtil;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedisUtil redisUtil;
    private static long refreshExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    public void setRefreshExpirationTime(long refreshExpirationTime) {
        TokenService.refreshExpirationTime = refreshExpirationTime;
    }

    public void saveRefreshToken(String email, String refreshToken){
        redisUtil.setRefreshToken(email, refreshToken, refreshExpirationTime);
    }

    public boolean validateRefreshToken(String email, String refreshToken){
        String key = "refreshToken:" + email;
        String refreshTokenFindByKey = redisUtil.getData(key);

        if(refreshTokenFindByKey == null) {
            return false;
        }
        return refreshTokenFindByKey.equals(refreshToken);
    }

    public String createNewAccessToken(String refreshToken){
        String email = JwtUtil.parseToken(refreshToken).get("email", String.class);
        return JwtUtil.createAccessToken(email);
    }

}
