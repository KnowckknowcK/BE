package com.knu.KnowcKKnowcK.utils;

import org.springframework.stereotype.Component;

@Component
public class DebateRoomUtil {
    public static double calculateRatio(long agree, long disagree){
        if(agree == 0 && disagree == 0) return 0;
        return (double) agree / (agree + disagree) * 100;
    }
}
