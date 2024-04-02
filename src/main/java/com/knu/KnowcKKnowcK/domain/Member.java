package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    public Member(String name, String email, String profileImage, Boolean isOAuth, String password) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.isOAuth = isOAuth;
        this.password = password;
    }

}
