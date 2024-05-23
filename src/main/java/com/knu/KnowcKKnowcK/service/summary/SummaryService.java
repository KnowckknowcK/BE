package com.knu.KnowcKKnowcK.service.summary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Option;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.chatGptService.ChatGptContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;

    private final SummaryFeedbackService summaryFeedbackService;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final ChatGptContext chatGptContext;

    @Transactional
    public SummaryResponseDto saveSummary(SummaryRequestDto dto, String writer) {
        return summaryStatusCheck(dto, writer, articleRepository, memberRepository, summaryRepository);
    }

    public static SummaryResponseDto summaryStatusCheck(SummaryRequestDto dto, String writer, ArticleRepository articleRepository, MemberRepository memberRepository, SummaryRepository summaryRepository) {
        if(!dto.getStatus().equals(Status.ING)){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findByEmail(writer).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        summaryRepository.findByArticleAndWriter(article,member);

        summaryRepository.findByArticleAndWriter(article, member)
                .ifPresentOrElse(
                        summary -> summary.update(dto.getContent(), dto.getStatus(), dto.getTakenTime()),
                        () -> summaryRepository.save(dto.toEntity(article, member))
                );

        return new SummaryResponseDto("임시 저장이 완료되었습니다.");
    }


    public SummaryResponseDto getSummaryFeedback(SummaryRequestDto dto, String writer) {
        if (dto.getStatus() != Status.DONE)
            throw new CustomException(ErrorCode.INVALID_INPUT);

        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.getUserByEmail(writer);

        Pair<Score, String> parsedFeedback = chatGptContext.callGptApi(Option.SUMMARY, article.getContent(), dto.getContent());

        return new SummaryResponseDto(summaryFeedbackService.saveSummaryFeedback
                (dto.toEntity(article, member), parsedFeedback, member));
    }

    public SummaryHistoryResponseDto loadSummaryHistory(String userEmail, long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Optional<Summary> existedSummary = summaryRepository.findByArticleAndWriter(article, member);

        if (existedSummary.isEmpty() || (existedSummary.isPresent() && existedSummary.get().getStatus() == Status.DONE)) {
            return new SummaryHistoryResponseDto(Status.NEW);
        }

        return new SummaryHistoryResponseDto(existedSummary.get());
    }
}
