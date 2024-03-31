package com.knu.KnowcKKnowcK.service.chatGptService;

import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.OpinionPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionFeedbackService  implements ChatGptService{

    private final ChatGptConfig chatGptConfig;
    @Override
    public String callGptApi(String article, String summary) {
        List<ChatgptRequestDto.Message> messageList = new ArrayList<>();
        messageList.add(new ChatgptRequestDto.Message("user", OpinionPrompt.getInstance().getPrompt() + article + summary));
        ChatgptRequestDto requestDto = new ChatgptRequestDto(messageList);


        ChatgptResponseDto responseDto = chatGptConfig.gptClient()
                .post()
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(ChatgptResponseDto.class)
                .block();
        String result = responseDto.getChoices().get(0).getMessage().getContent();


        return result;
    }
}
