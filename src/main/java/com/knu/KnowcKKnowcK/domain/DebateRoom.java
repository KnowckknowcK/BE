package com.knu.KnowcKKnowcK.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class DebateRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    private String title;
    private Double ratio;
    private Double score;

    // DebateRoom이 삭제되면 그와 연관된 MemberDebate도 삭제 (JPA 활용 시)
    @OneToMany(mappedBy = "debateRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberDebate> memberDebates = new HashSet<>();
}
