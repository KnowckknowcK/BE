package com.knu.KnowcKKnowcK.service.chatGptService;


import org.springframework.data.util.Pair;

public interface ChatGptService {
    Pair<Integer, String> callGptApi(String article, String summary);
}
