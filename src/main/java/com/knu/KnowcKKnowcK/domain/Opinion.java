package com.knu.KnowcKKnowcK.domain;

import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member writer;
  
    @ManyToOne
    private Article article;
  
    @Lob
    private String content;
  
    @Enumerated(EnumType.STRING)
    private Position position;
  
    @Enumerated(EnumType.STRING)
    private Status status;
  
    private LocalDateTime createdTime;
  
      public Opinion update(String content, Status status) {
      this.content = content;
      this.status = status;

      return this;
  }
}
