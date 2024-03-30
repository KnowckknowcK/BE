package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
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
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Summary> summaries = new ArrayList<>();

    //사용자가 작성한 견해
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Opinion> opinions = new ArrayList<>();


}
