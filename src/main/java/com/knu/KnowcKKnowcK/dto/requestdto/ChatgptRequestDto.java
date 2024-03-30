package com.knu.KnowcKKnowcK.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatgptRequestDto implements Serializable {
    private List<Message> messages;
    @Data
    @AllArgsConstructor
    public static class Message{
        private String role;
        private String content;
    }
    private String model = "gpt-3.5-turbo";
    @JsonProperty("max_tokens")
    private Integer maxTokens = 100;

//    private Double temperature=1.0;
//    @JsonProperty("stop")
//    private String stop_sequences="\n";
//    @JsonProperty("top_p")
//    private Double topP=1.0;
//    @JsonProperty("frequency_penalty")
//    private Double frequency_penalty=0.0;
//    @JsonProperty("presence_penalty")
//    private Double presence_penalty=0.0;

    public ChatgptRequestDto(List<Message> messages){
        this.messages = messages;
    }
}