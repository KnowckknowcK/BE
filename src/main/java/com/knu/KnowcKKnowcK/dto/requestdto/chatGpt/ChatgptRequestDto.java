package com.knu.KnowcKKnowcK.dto.requestdto.chatGpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatgptRequestDto implements Serializable {
    private List<Message> messages = new ArrayList<>();

    private String model = "gpt-3.5-turbo";
//    @JsonProperty("max_tokens")
//    private Integer maxTokens = 200;

//    private Double temperature=1.0;
//    @JsonProperty("stop")
//    private String stop_sequences="\n";
//    @JsonProperty("top_p")
//    private Double topP=1.0;
//    @JsonProperty("frequency_penalty")
//    private Double frequency_penalty=0.0;
//    @JsonProperty("presence_penalty")
//    private Double presence_penalty=0.0;

    public ChatgptRequestDto(Message messages){
        this.messages.add(messages);
    }

    @Override
    public String toString(){
        return "role: "+this.messages.get(0).getRole()+"\n"+
                "content:\n"+this.messages.get(0).getContent();
    }



}