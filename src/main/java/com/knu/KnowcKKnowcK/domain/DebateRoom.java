package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class DebateRoom {
    @Id
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

    @OneToMany(mappedBy = "debateRoom", fetch = FetchType.LAZY)
    private List<MemberDebate> memberDebates;

    @Builder
    public DebateRoom(Article article){
        this.id = article.getId();
        this.article = article;
        this.title = article.getTitle();
        this.agreeNum = 0L;
        this.disagreeNum = 0L;
        this.agreeLikesNum = 0L;
        this.disagreeLikesNum = 0L;
    }
}
