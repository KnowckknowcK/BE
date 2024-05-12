package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.OpinionPrompt;
import com.knu.KnowcKKnowcK.dto.prompt.SummaryPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.chatGpt.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.chatGpt.Message;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionFeedbackClient implements ChatGptClient {

    private final ChatGptConfig chatGptConfig;
    @Override
    public Pair<Score, String> callGptApi(String article, String opinion) {
        ChatgptRequestDto requestDto = new ChatgptRequestDto(
                Message.promptContent(article, opinion, SummaryPrompt.getInstance().getPrompt()));

        ChatgptResponseDto responseDto = chatGptConfig.gptClient()
                .post()
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(ChatgptResponseDto.class)
                .block();

        return Pair.of(Score.FAIR, responseDto.getChoices().get(0).getMessage().getContent());
    }
}
