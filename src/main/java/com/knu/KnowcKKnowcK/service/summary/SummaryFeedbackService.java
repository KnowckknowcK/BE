package com.knu.KnowcKKnowcK.service.summary;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SummaryFeedbackService {
    private final SummaryRepository summaryRepository;

    private final SummaryFeedbackRepository summaryFeedbackRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public SummaryFeedback saveSummaryFeedback(Summary summary, Pair<Score, String> parsedFeedback, Member member){
        summaryRepository.findByArticleAndWriter(summary.getArticle(), member)
                .ifPresent(summaryRepository::delete);
        summaryRepository.flush();
        Summary savedSummary = summaryRepository.save(summary);

        member.setPoint(member.getPoint() + parsedFeedback.getFirst().getExp());
        memberRepository.save(member);

        return summaryFeedbackRepository.save(SummaryFeedback.from(savedSummary,parsedFeedback));
    }
}
