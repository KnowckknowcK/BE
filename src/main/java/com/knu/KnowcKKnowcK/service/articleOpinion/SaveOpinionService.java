package com.knu.KnowcKKnowcK.service.articleOpinion;

import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;

public interface SaveOpinionService {

    OpinionResponseDto getOpinionFeedback(OpinionRequestDto dto);

}
