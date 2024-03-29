package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;


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

}
