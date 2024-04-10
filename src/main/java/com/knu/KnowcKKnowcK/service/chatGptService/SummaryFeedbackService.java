package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.SummaryPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryFeedbackService implements ChatGptService{
    private final ChatGptConfig chatGptConfig;
    @Override
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

    private Pair<Score, String> parsingFeedback(String content){

            String[] split = content.split("#");
            String score = split[0];
            String feedback = split[1];

        try {
            return Pair.of(Score.valueOf(score), feedback);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

    }
}
