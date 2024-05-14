package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;

public interface LoadSummaryService {

    SummaryHistoryResponseDto loadSummaryHistory(String userEmail, long articleId);
}
