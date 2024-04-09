package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.enums.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptServiceTest {
    private final ChatGptContext chatGptContext;

    public Pair<String, String> test(){
        // Option.OPINION or Option.SUMMARY 로 요약인지 견해인지를 구분
        return chatGptContext.callGptApi(Option.OPINION,"오늘의 날씨","오늘 날씨");
    }
}
