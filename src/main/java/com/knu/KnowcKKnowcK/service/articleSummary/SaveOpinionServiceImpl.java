package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.service.chatGptService.OpinionFeedbackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveOpinionServiceImpl  implements SaveOpinionService{
    private final OpinionRepository opinionRepository;

    private final OpinionFeedbackRepository opinionFeedbackRepository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final OpinionFeedbackService opinionFeedbackService;

    @Override
    @Transactional
    public OpinionResponseDto saveOpinion(OpinionRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Optional<Opinion> existedOpinion = opinionRepository.findByArticleAndWriter(article, member);
        Opinion savedOpinion;

        if (existedOpinion.isPresent()){
            savedOpinion = existedOpinion.get().update(dto.getContent(), dto.getStatus());
        } else {
            savedOpinion = opinionRepository.save(dto.toEntity(article, member));
        }

        if (savedOpinion.getStatus().equals(Status.ING)){
            return new OpinionResponseDto("임시 저장이 완료되었습니다.");

        } else if (savedOpinion.getStatus().equals(Status.DONE)) {
            Pair<Score, String> parsedFeedback = opinionFeedbackService.callGptApi(article.getContent(), savedOpinion.getContent());

            OpinionFeedback opinionFeedback = OpinionFeedback.builder()
                    .content(parsedFeedback.getSecond())
                    .opinion(savedOpinion)
                    .build();

            OpinionFeedback savedFeedback = opinionFeedbackRepository.save(opinionFeedback);
            return new OpinionResponseDto(savedFeedback);

        } else {
            throw new CustomException(ErrorCode.FAILED);

        }
    }
}
