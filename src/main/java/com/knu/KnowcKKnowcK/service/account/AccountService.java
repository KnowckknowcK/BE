package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;


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
