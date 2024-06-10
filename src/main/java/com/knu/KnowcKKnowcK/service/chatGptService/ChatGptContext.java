package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.enums.Option;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ChatGptContext {
    private final SummaryFeedbackClient summaryFeedbackClient;
    private final OpinionFeedbackClient opinionFeedbackClient;


    public Pair<Score, String> callGptApi(Option op, String article, String answer){
        if(op==Option.SUMMARY)
            return this.summaryFeedbackClient.callGptApi(article, answer);
        else
            return this.opinionFeedbackClient.callGptApi(article, answer);
    }
}
