package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyDebateRoomResponseDto {
    private Position position;
    private String title;
    private Long agreeNum;
    private Long disagreeNum;

    public MyDebateRoomResponseDto(Position position,DebateRoom debateRoom){
        this.position = position;
        this.title = debateRoom.getTitle();
        this.agreeNum = debateRoom.getAgreeNum();
        this.disagreeNum = debateRoom.getDisagreeNum();
    }
}
