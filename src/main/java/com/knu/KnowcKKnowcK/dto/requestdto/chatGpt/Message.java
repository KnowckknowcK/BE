package com.knu.KnowcKKnowcK.dto.requestdto.chatGpt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토론방 메세지와는 별개의 클래스임
 */
@Getter
@NoArgsConstructor
public class Message{
    private String role;
    private String content;

    @Builder
    private Message(String content){
        this.role="user";
        this.content = content;
    }

    public static Message promptContent(String article, String summary, String prompt){
        return Message.builder()
                .content("기사내용\n"+article+"\n\n"+
                        "요약내용\n"+summary+"\n\n"+
                        prompt)
                .build();
    }
}