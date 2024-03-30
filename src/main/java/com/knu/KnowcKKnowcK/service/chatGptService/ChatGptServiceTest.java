package com.knu.KnowcKKnowcK.service.chatGptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatGptServiceTest {
    @Autowired
    @Qualifier("opinionFeedbackService") // or summaryFeedbackService
    private ChatGptService chatGptService;

    public String test(){
        return chatGptService.callGptApi("오늘의 날씨","오늘 날씨");
    }
}
