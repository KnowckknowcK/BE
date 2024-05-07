package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.chatGptService.SummaryFeedbackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaveSummaryServiceImpl implements SaveSummaryService{


    private final SummaryRepository summaryRepository;

    private final SummaryFeedbackRepository summaryFeedbackRepository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final SummaryFeedbackService summaryFeedbackService;

    @Override
    @Transactional
    public SummaryResponseDto saveSummary(SummaryRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        summaryRepository.findByArticleAndWriter(article, member)
                .ifPresentOrElse(
                summary -> summary.update(dto.getContent(), dto.getStatus(), dto.getTakenTime()),
                () -> summaryRepository.save(dto.toEntity(article, member))
                );

        if(!dto.getStatus().equals(Status.ING)){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        return new SummaryResponseDto("임시 저장이 완료되었습니다.");
    }

    @Override
    @Transactional
    public SummaryResponseDto getSummaryFeedback(SummaryRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        summaryRepository.findByArticleAndWriter(article, member)
                .ifPresent((summary) -> summaryRepository.delete(summary));

        Summary savedSummary = summaryRepository.save(dto.toEntity(article, member));

        if(!dto.getStatus().equals(Status.DONE)){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        Pair<Score, String> parsedFeedback = summaryFeedbackService.callGptApi(article.getContent(), savedSummary.getContent());

        SummaryFeedback summaryFeedback = SummaryFeedback.builder()
                .score(parsedFeedback.getFirst())
                .content(parsedFeedback.getSecond())
                .summary(savedSummary)
                .build();

        SummaryFeedback savedFeedback = summaryFeedbackRepository.save(summaryFeedback);
        return new SummaryResponseDto(savedFeedback);
    }
}
