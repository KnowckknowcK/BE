package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.service.myPage.MySummaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MySummaryControllerTest {

    @InjectMocks
    private MySummaryService mySummaryService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private SummaryFeedbackRepository summaryFeedbackRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Test
    @DisplayName("사용자가 작성중인 요약문 확인")
    void getMySummary() {

        Member member = new Member("더미1","test@gmail.com","url",false,"password");
        memberRepository.save(member);
        Summary summary = new Summary(1L,member,null, "content", LocalDateTime.now(), Status.DONE,50L);
        summaryRepository.save(summary);
        SummaryFeedback summaryFeedback = new SummaryFeedback(1L,"good", Score.EXCELLENT,summary);
        summaryFeedbackRepository.save(summaryFeedback);

        Summary summaryING = new Summary(1L,member,null, "content",LocalDateTime.now(), Status.ING,50L);
        summaryRepository.save(summaryING);

        List<?> answer = mySummaryService.getMySummaries(1L,Status.ING);
        System.out.println(answer);
    }
}