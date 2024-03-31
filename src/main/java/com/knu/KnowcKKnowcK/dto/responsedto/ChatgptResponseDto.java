package com.knu.KnowcKKnowcK.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class ChatgptResponseDto implements Serializable {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice{
        private Message message;
        private Integer index;
        @JsonProperty("finish_reason")
        private String finishReason;

        @Data
        public static class Message{
            private String role;
            private String content;
        }
    }
}