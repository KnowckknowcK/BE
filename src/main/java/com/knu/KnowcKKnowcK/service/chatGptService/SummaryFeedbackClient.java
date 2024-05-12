package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.SummaryPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryFeedbackClient implements ChatGptClient {
    private final ChatGptConfig chatGptConfig;
    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 4000), include = {ArrayIndexOutOfBoundsException.class, IllegalArgumentException.class })
    public Pair<Score, String> callGptApi(String article, String summary) {

        List<ChatgptRequestDto.Message> messageList = new ArrayList<>();
        messageList.add(new ChatgptRequestDto.Message("user", SummaryPrompt.getInstance().getPrompt() + article + summary));
        ChatgptRequestDto requestDto = new ChatgptRequestDto(messageList);

        ChatgptResponseDto responseDto = chatGptConfig.gptClient()
                .post()
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(ChatgptResponseDto.class)
                .block();

        return parsingFeedback(responseDto.getChoices().get(0).getMessage().getContent());

    }



    private Pair<Score, String> parsingFeedback(String content) throws ArrayIndexOutOfBoundsException {

        System.out.println("content = " + content);
        String[] split = content.split("#");
        String score = split[0];
        String feedback = split[1];

        return Pair.of(Score.valueOf(score), feedback);

    }
}
