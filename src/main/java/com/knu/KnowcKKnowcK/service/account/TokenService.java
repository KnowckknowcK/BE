package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.dto.responsedto.NewAuthResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_EXPIRED;
import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_INVALID;


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

    public NewAuthResponseDto createNewAccessToken(String refreshToken) {
        String email = getEmailFromToken(refreshToken);

        if(validateRefreshToken(email, refreshToken)){
            return NewAuthResponseDto.builder().refreshToken(JwtUtil.createAccessToken(email)).build();
        }
        else{
            throw new CustomException(TOKEN_INVALID);
        }
    }

    public String getEmailFromToken (String refreshToken){
        try{
            return JwtUtil.parseToken(refreshToken).get("email", String.class);
        } catch (ExpiredJwtException e) {
            throw new CustomException(TOKEN_EXPIRED);
        }
    }

}
