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
import java.util.Optional;


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
        Optional<Summary> existedSummary = summaryRepository.findByArticleAndWriter(article, member);
        Summary savedSummary;

        if (existedSummary.isPresent()){
            if(existedSummary.get().getStatus().equals(Status.ING) && existedSummary.get().getTakenTime() > dto.getTakenTime()){
                throw new CustomException(ErrorCode.INVALID_INPUT);
            }
            savedSummary = existedSummary.get().update(dto.getContent(), dto.getStatus(), dto.getTakenTime());
        } else {
            savedSummary = summaryRepository.save(dto.toEntity(article, member));
        }

        if (savedSummary.getStatus().equals(Status.ING)){
            return new SummaryResponseDto("임시 저장이 완료되었습니다.");

        } else if (savedSummary.getStatus().equals(Status.DONE)) {
            Pair<Score, String> parsedFeedback = summaryFeedbackService.callGptApi(article.getContent(), savedSummary.getContent());

            SummaryFeedback summaryFeedback = SummaryFeedback.builder()
                    .score(parsedFeedback.getFirst())
                    .content(parsedFeedback.getSecond())
                    .summary(savedSummary)
                    .build();

            SummaryFeedback savedFeedback = summaryFeedbackRepository.save(summaryFeedback);
            return new SummaryResponseDto(savedFeedback);

        } else {
            throw new CustomException(ErrorCode.FAILED);
        }
    }
}
