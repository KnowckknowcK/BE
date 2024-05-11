package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
public class DebateRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    private String title;

    private Long agreeNum;
    private Long agreeLikesNum;
    private Long disagreeNum;
    private Long disagreeLikesNum;


    @Builder
    public DebateRoom(Article article){
        this.id = article.getId();  // 애초에 기사-토론방은 1대1 관계라 id 필드가 필요 없었을 거 같아요.
        this.article = article;
        this.title = article.getTitle();
        this.agreeNum = 0L;
        this.disagreeNum = 0L;
        this.agreeLikesNum = 0L;
        this.disagreeLikesNum = 0L;
    }
}
