package com.knu.KnowcKKnowcK.service.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.SignupRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.GoogleLoginResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SigninResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.utils.AwsS3Util;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import static com.knu.KnowcKKnowcK.apiResponse.SuccessCode.CREATED_SUCCESS;
import static com.knu.KnowcKKnowcK.exception.ErrorCode.ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;
    private final Long expiredAt = 1000 * 60 * 60L; //1H
    private final AuthenticationConfiguration authenticationConfiguration;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AwsS3Util awsS3Util;

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

    public HttpStatus signupWithEmail(String userInfo, MultipartFile profileImg) throws JsonProcessingException {

        String profileImgUrl = "";

        ObjectMapper objectMapper = new ObjectMapper();
        SignupRequestDto requestDto = objectMapper.readValue(userInfo, SignupRequestDto.class);

        Optional<Member> member = memberRepository.findByEmail(requestDto.getEmail());

        if (member.isPresent())
            return ALREADY_REGISTERED.getError();

        if (profileImg == null || profileImg.isEmpty()) {
            profileImgUrl = randomDefaultImg();
        }
        else{
            profileImgUrl = awsS3Util.uploadFile(profileImg);
        }


        Member newMember = Member.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .profileImage(profileImgUrl)
                .isOAuth(false)
                .level(0L)
                .point(0L)
                .build();

        memberRepository.save(newMember);

        return CREATED_SUCCESS.getSuccess();
    }

    public GoogleLoginResponseDto returnToken(String email) {

        if (email.isBlank()) {
            return null;
        } else {
            return GoogleLoginResponseDto.builder()
                    .jwt(JwtUtil.creatJWT(email, expiredAt))
                    .build();
        }
    }

    public String randomDefaultImg() {

        String[] defaultImg = {
                "https://knowckknowck-bucket.s3.ap-northeast-2.amazonaws.com/profile/profile1.png",
                "https://knowckknowck-bucket.s3.ap-northeast-2.amazonaws.com/profile/profile2.png",
                "https://knowckknowck-bucket.s3.ap-northeast-2.amazonaws.com/profile/profile3.png",
                "https://knowckknowck-bucket.s3.ap-northeast-2.amazonaws.com/profile/profile4.png"
        };

        Random random = new Random();
        int imgNumber = random.nextInt(4);

        return defaultImg[imgNumber];
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
