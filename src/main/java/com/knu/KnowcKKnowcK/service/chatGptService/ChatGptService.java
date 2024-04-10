package com.knu.KnowcKKnowcK.service.chatGptService;


import com.knu.KnowcKKnowcK.enums.Score;
import org.springframework.data.util.Pair;

public interface ChatGptService {
    Pair<Score, String> callGptApi(String article, String summary);
}
