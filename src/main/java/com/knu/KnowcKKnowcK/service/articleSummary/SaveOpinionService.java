package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;

public interface SaveOpinionService {

    OpinionResponseDto saveOpinion(OpinionRequestDto dto);

}
