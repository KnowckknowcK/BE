package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.responsedto.SigninResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SignupResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredAt = 1000 * 60 * 60L; //1H


    public SigninResponseDto signinWithEmail(String email, String password) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty())
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        if (member.get().getIsOAuth()) {
            throw new CustomException(ErrorCode.ALREADY_REGISTERED);
        }

        if (member.get().getPassword().equals(password)) {
            SigninResponseDto responseDto = SigninResponseDto.builder()
                    .email(member.get().getEmail())
                    .name(member.get().getName())
                    .profileImg(member.get().getProfileImage())
                    .jwt(JwtUtil.creatJWT(member.get().getEmail(), secretKey, expiredAt))
                    .build();

            return responseDto;
        } else {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    public SignupResponseDto signupWithEmail(String email, String name, String password, String profileImg) {

        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent())
            throw new CustomException(ErrorCode.ALREADY_REGISTERED);

        Member newMember = Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .profileImage(profileImg)
                .isOAuth(false)
                .build();

        Member savedMember = memberRepository.save(newMember);

        SignupResponseDto responseDto = SignupResponseDto.builder()
                .email(savedMember.getEmail())
                .name(savedMember.getName())
                .profileImg(savedMember.getProfileImage())
                .jwt(JwtUtil.creatJWT(savedMember.getEmail(), secretKey, expiredAt))
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
