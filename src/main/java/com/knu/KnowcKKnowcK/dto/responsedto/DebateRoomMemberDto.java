package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Member;
import lombok.Data;

@Data
public class DebateRoomMemberDto {
    private Long id;
    private String profileImage;
    private String name;
    private String position;

    public DebateRoomMemberDto(Member member, String position){
        this.id = member.getId();
        this.name = member.getName();
        this.profileImage = member.getProfileImage();
        this.position = position;
    }
}
