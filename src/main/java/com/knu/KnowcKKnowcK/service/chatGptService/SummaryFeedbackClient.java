package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.SummaryPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.chatGpt.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.chatGpt.Message;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

@Service
@RequiredArgsConstructor
public class SummaryFeedbackClient implements ChatGptClient {
    private final ChatGptConfig chatGptConfig;
    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 4000), include = {NullPointerException.class })
    public Pair<Score, String> callGptApi(String article, String summary) {
        ChatgptRequestDto requestDto = new ChatgptRequestDto(
                Message.promptContent(article, summary, SummaryPrompt.getInstance().getPrompt()));

        ChatgptResponseDto responseDto = chatGptConfig.gptClient()
                .post()
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(ChatgptResponseDto.class)
                .block();

        return parsingFeedback(responseDto.getChoices().get(0).getMessage().getContent());

    }



    private Pair<Score, String> parsingFeedback(String content) {
        String[] split = content.split("#");
        try{
            String score = split[0];
            String feedback = split[1];
            return Pair.of(Score.valueOf(score), feedback);
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exception){
            return Pair.of(Score.GOOD, content);
        }
    }
}
