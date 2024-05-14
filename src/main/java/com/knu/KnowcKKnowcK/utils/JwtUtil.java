package com.knu.KnowcKKnowcK.utils;

import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.service.account.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_EXPIRED;
import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_INVALID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static String secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }

    private final UserDetailsServiceImpl userDetailsService;

    public static String creatJWT(String email, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //헤더에서 토큰 가져오기
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    //authentication 객체 반환
    public Authentication getAuthentication(String token) {
        String userPrincipal = Jwts.parser().
                setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(ExpiredJwtException e) {
//            log.error(TOKEN_EXPIRED.getMessage());
            throw new CustomException(TOKEN_EXPIRED);
        } catch(JwtException e) {
//            log.error(TOKEN_INVALID.getMessage());
            throw new CustomException(TOKEN_INVALID);
        }
    }

}
