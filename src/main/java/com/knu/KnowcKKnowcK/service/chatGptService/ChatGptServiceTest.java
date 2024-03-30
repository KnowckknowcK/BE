package com.knu.KnowcKKnowcK.service.chatGptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatGptServiceTest {
    @Qualifier("opinionFeedbackService") // or summaryFeedbackService
    private final ChatGptService chatGptService;
    @Autowired
    public ChatGptServiceTest( ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    public String test(){
        return chatGptService.callGptApi("오늘의 날씨","오늘 날씨");
    }
}
