package com.knu.KnowcKKnowcK.service.debateRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DebateRoomUtil {
    public static double calculateRatio(long agree, long disagree){
        if(agree == 0 && disagree == 0) return 0;
        return (double) agree / (agree + disagree) * 100;
    }

    public static LocalDateTime formatToLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    public static String getDebateRoomKey(Long debateRoomId){
        return "debateRoom:" + debateRoomId + ":messages";
    }

    public static String getMessageThreadKey(Long messageId){
        return "message:" + messageId;
    }
}
