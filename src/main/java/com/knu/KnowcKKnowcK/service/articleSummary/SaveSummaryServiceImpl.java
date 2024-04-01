package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaveSummaryServiceImpl implements SaveSummaryService{


    private final SummaryRepository summaryRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Override
    public SummaryResponseDto saveSummary(SummaryRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        Member member = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        Summary summary = dto.toEntity(article, member);
        Summary savedSummary = summaryRepository.save(summary);

        if (savedSummary.getStatus().equals(Status.ING)){
            return new SummaryResponseDto("임시 저장이 완료되었습니다.");

        }

        // Status.DONE 인 경우 피드백 요청 시작
        throw new CustomException(ErrorCode.FAILED);
    }
}
