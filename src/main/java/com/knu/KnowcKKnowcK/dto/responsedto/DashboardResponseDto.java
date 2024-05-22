package com.knu.KnowcKKnowcK.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardResponseDto {
    //day strike, 오늘 쓴 요약, 총 견해 수, 총 요약 수
    private Long todayWorks = 0L;
    private int totalSummaries = 0;
    private int totalOpinions = 0;
    private int strikes;
    private Long point = 0L;
}
