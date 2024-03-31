package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;

public interface SaveSummaryService {

  SummaryResponseDto saveSummary(SummaryRequestDto dto);
}
