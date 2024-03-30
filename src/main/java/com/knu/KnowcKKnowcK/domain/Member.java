package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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

    @Builder
    public Member(String name, String email, String profileImage, Boolean isOAuth) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.isOAuth = isOAuth;
    }

    public Member update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
        return this;
    }

    //생명주기는 부모쪽에서 관리함
    //사용자가 작성한 요약문
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Summary> summaries = new ArrayList<>();

    //사용자가 작성한 견해
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Opinion> opinions = new ArrayList<>();


}
