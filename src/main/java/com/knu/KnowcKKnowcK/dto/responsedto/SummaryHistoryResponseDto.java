package com.knu.KnowcKKnowcK.dto.responsedto;

import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.enums.Status;
import lombok.Getter;

@Getter
public class SummaryHistoryResponseDto {

    public long id;

    public long takenTime;

    public String content;

    public Status status;

    public SummaryHistoryResponseDto(Summary summary) {
        this.id = summary.getId();
        this.takenTime = summary.getTakenTime();
        this.content = summary.getContent();
        this.status = summary.getStatus();
    }

    public SummaryHistoryResponseDto(Status status) {
        this.status = status;
    }
}
