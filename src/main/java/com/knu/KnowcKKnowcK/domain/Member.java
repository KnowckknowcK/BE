package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String profileImage;
    private String name;
    private Long point;
    private Long level;
    private Boolean isOAuth;

    //생명주기는 부모쪽에서 관리함
    //사용자가 작성한 요약문
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Summary> summaries = new ArrayList<>();

    //사용자가 작성한 견해
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opinion> opinions = new ArrayList<>();

    @Builder
    public Member(String name, String email, String profileImage, Boolean isOAuth, String password) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.isOAuth = isOAuth;
        this.password = password;
    }

    public Member update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
        return this;
    }

    //프로필 수정
    public void updateProfile(String name, String email, String profileImage) {
        //닉네임, 계정, 이메일, 프로필 이미지 변경
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }
}
