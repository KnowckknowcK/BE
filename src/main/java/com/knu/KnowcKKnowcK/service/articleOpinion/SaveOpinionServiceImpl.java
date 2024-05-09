package com.knu.KnowcKKnowcK.service.articleOpinion;

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

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final OpinionFeedbackService opinionFeedbackService;

    @Override
    @Transactional
    public OpinionResponseDto getOpinionFeedback(OpinionRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Optional<Opinion> existedOpinion = opinionRepository.findByArticleAndWriter(article, member);

         if (existedOpinion.isPresent()) {
             throw new CustomException(ErrorCode.INVALID_INPUT);
         }

        Pair<Score, String> feedback = opinionFeedbackService.callGptApi(article.getContent(), dto.getContent());

        Opinion opinion = Opinion.builder()
                .feedbackContent(feedback.getSecond())
                .content(dto.getContent())
                .writer(member)
                .article(article)
                .status(Status.DONE)
                .position(dto.getPosition())
                .build();

        opinionRepository.save(opinion);

        return new OpinionResponseDto(opinion);
    }
}