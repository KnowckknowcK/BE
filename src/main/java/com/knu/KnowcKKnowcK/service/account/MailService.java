package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.utils.MailUtil;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private final MailUtil mailUtil;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendMail(String email) throws MessagingException {

        if (redisUtil.getData(email) != null){
            redisUtil.deleteData(email);
        }

        try {
            String subject = "똑똑(KnowcKKnowck)! 이메일 인증코드를 확인하세요.";

            String code = createdCode();
            String mailTemplate = mailUtil.mailTemplate(code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

            messageHelper.setFrom(sender);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailTemplate, true);

            mailSender.send(message);
            saveCode(email, code);

            return "이메일 인증코드 발송이 완료되었습니다.";

        } catch (MessagingException e) {
            return e.toString();
        }

    }

    public void saveCode(String email, String code) {
        Long expiredMs = 1000*60*5L; //5분
        redisUtil.setData(email, code, expiredMs);
    }

    public boolean checkCode(String email, String code) {

        String codeFindByEmail = redisUtil.getData(email);

        if(codeFindByEmail == null) {
            return false;
        }

        redisUtil.deleteData(email);

        return codeFindByEmail.equals(code);
    }

    private String createdCode() {
        int leftLimit = 48; //'0'
        int rightLimit = 122; //'z'
        int targetStringLength = 6; //6자리 인증코드
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <=57 || i >=65) && (i <= 90 || i>= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

