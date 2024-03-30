package com.knu.KnowcKKnowcK.service.chatGptService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.KnowcKKnowcK.config.ChatGptConfig;
import com.knu.KnowcKKnowcK.dto.prompt.SummaryPrompt;
import com.knu.KnowcKKnowcK.dto.requestdto.ChatgptRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ChatgptResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryFeedbackService implements ChatGptService{
    private final ChatGptConfig chatGptConfig;
    @Override
    public String callGptApi(String article, String summary) {

        List<ChatgptRequestDto.Message> messageList = new ArrayList<>();
        messageList.add(new ChatgptRequestDto.Message("user", SummaryPrompt.getInstance().getPrompt() + article + summary));
        ChatgptRequestDto requestDto = new ChatgptRequestDto(messageList);


        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(requestDto);
            System.out.println(jsonString);
        }catch (JsonProcessingException e){}

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
