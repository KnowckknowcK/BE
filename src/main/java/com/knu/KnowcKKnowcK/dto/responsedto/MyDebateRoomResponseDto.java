package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyDebateRoomResponseDto {
    private Long id;
    private Position position;
    private String title;
    private Long agreeLikeNum;
    private Long disagreeLikeNum;

    public MyDebateRoomResponseDto(Position position,DebateRoom debateRoom){
        this.id = debateRoom.getId();
        this.position = position;
        this.title = debateRoom.getTitle();
        this.agreeLikeNum = debateRoom.getAgreeLikesNum();
        this.disagreeLikeNum = debateRoom.getDisagreeLikesNum();
    }
}
