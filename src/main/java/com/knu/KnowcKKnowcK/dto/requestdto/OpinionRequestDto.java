package com.knu.KnowcKKnowcK.dto.requestdto;
import com.knu.KnowcKKnowcK.enums.Position;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class OpinionRequestDto {

    @NotNull(message = "articleId는 null일 수 없습니다.")
    private Long articleId;

    @NotNull(message = "내용은 null일 수 없습니다.")
    private String content;

    private LocalDateTime createdTime;

    @NotNull(message = "Position은 null일 수 없습니다.")
    private Position position;
}
