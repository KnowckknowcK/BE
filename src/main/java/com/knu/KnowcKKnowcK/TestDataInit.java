package com.knu.KnowcKKnowcK;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;
    private final SummaryRepository summaryRepository;
    private final SummaryFeedbackRepository summaryFeedbackRepository;

    @PostConstruct
    public void init(){
        Member member = new Member("더미1","test@gmail.com","url",false,"password");
        memberRepository.save(member);
        Summary summary = new Summary(1L,member,null, "content",LocalDateTime.now(), Status.DONE,50L);
        summaryRepository.save(summary);
        SummaryFeedback summaryFeedback = new SummaryFeedback(1L,"good", Score.EXCELLENT,summary);
        summaryFeedbackRepository.save(summaryFeedback);

        Summary summaryING = new Summary(1L,member,null, "content",LocalDateTime.now(), Status.ING,50L);
        summaryRepository.save(summaryING);

    }
}
