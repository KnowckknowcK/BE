package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.responsedto.SigninResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SignupResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;
    private final Long expiredAt = 1000 * 60 * 60L; //1H
    private final AuthenticationConfiguration authenticationConfiguration;
    private final BCryptPasswordEncoder passwordEncoder;

    public SigninResponseDto signinWithEmail(String email, String password) throws CustomException{
        try {
            //security 인증 사용(아이디 비밀번호 일치여부, 데이터베이스에 사용자 정보 존재 여부 등)
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            Optional<Member> authenticatedMember = memberRepository.findByEmail(authentication.getName());

            SigninResponseDto responseDto = SigninResponseDto.builder()
                    .email(authenticatedMember.get().getEmail())
                    .name(authenticatedMember.get().getName())
                    .profileImg(authenticatedMember.get().getProfileImage())
                    .jwt(JwtUtil.creatJWT(authentication.getName(), expiredAt))
                    .build();

            return responseDto;
        }catch (BadCredentialsException e) {
            System.out.println("exception");
            throw new CustomException(ErrorCode.INVALID_INPUT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SignupResponseDto signupWithEmail(String email, String name, String password, String profileImg) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent())
            throw new CustomException(ErrorCode.ALREADY_REGISTERED);

        Member newMember = Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .profileImage(profileImg)
                .isOAuth(false)
                .build();

        Member savedMember = memberRepository.save(newMember);

        SignupResponseDto responseDto = SignupResponseDto.builder()
                .email(savedMember.getEmail())
                .name(savedMember.getName())
                .profileImg(savedMember.getProfileImage())
                .jwt(JwtUtil.creatJWT(savedMember.getEmail(), expiredAt))
                .build();

        return responseDto;
    }

    public class MemberPasswordGenerator {

        private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        private static final String NUMBER = "0123456789";
        private static final String SPECIAL_CHARS = "!@#$%^&*()_-+=<>?";

        private static final String PASSWORD_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;

        public static String generateRandomPassword(int length) {
            SecureRandom random = new SecureRandom();
            StringBuilder password = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(PASSWORD_CHARS.length());
                password.append(PASSWORD_CHARS.charAt(index));
            }
            return password.toString();
        }
    }
}
