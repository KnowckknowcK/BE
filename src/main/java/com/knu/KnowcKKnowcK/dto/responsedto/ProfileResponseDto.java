package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    //닉네임, 계정, 이메일, 프로필 이미지 반환
    private String name;
    private String email;
    private String profileImage;
    private Long level = 0L;
    private Long point = 0L;

    public ProfileResponseDto(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.level = member.getLevel();
        this.point = member.getPoint();
    }
}
