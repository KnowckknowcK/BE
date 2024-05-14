package com.knu.KnowcKKnowcK.dto.requestdto;


import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class SummaryRequestDto {

    @NotNull(message = "articleId는 null일 수 없습니다.")
    private Long articleId;


    @NotNull(message = "내용은 null일 수 없습니다.")
    private String content;

    private LocalDateTime createdTime;

    @NotNull(message = "상태는 null일 수 없습니다.")
    private Status status;

    @NotNull(message = "소요시간은 null일 수 없습니다.")

    private Long takenTime;

    @Builder
    public Summary toEntity(Article article, Member writer) {
        return Summary.builder()
                .article(article)
                .content(content)
                .createdTime(LocalDateTime.now())
                .status(status)
                .takenTime(takenTime)
                .build();
    }
}
