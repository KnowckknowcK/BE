package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Option;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.chatGptService.ChatGptContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SaveSummaryServiceImpl implements SaveSummaryService{

    private final SummaryRepository summaryRepository;

    private final SummaryFeedbackRepository summaryFeedbackRepository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    private final ChatGptContext chatGptContext;

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
    @Transactional(readOnly=true)
    public SummaryResponseDto getSummaryFeedback(SummaryRequestDto dto) {
        if (dto.getStatus() != Status.DONE)
            throw new CustomException(ErrorCode.INVALID_INPUT);
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        Pair<Score, String> parsedFeedback = chatGptContext.callGptApi(Option.SUMMARY, article.getContent(), dto.getContent());

        return new SummaryResponseDto(saveSummaryFeedback(dto.toEntity(article, member),parsedFeedback));
    }


    @Transactional
    protected SummaryFeedback saveSummaryFeedback(Summary summary, Pair<Score, String> parsedFeedback){
        summaryRepository.findByArticleAndWriter(summary.getArticle(), summary.getWriter())
                .ifPresent(summaryRepository::delete);
        summaryRepository.flush();
        Summary savedSummary = summaryRepository.save(summary);
        return summaryFeedbackRepository.save(SummaryFeedback.from(savedSummary,parsedFeedback));
    }
}
