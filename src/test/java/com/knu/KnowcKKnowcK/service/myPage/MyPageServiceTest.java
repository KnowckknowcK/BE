package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private SummaryFeedbackRepository summaryFeedbackRepository;
    @InjectMocks
    private MyPageService myPageService;
    @Test
    void getProfile() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    @DisplayName("대시보드 정보 조회")
    void getDashboardInfo() {
        //given
        Member member = createMember();
        member.setId(1L);
        Article article1 = createArticle();
        Article article2 = createArticle();
        Summary summary1 = createSummary(member,article1,Status.DONE,LocalDateTime.now());
        Summary summary2 = createSummary(member,article2,Status.DONE,LocalDateTime.now().minusDays(1));
        Summary summary3 = createSummary(member,article1,Status.ING,LocalDateTime.now());

        SummaryFeedback feedback1 = createSummaryFeedback(summary1);
        SummaryFeedback feedback2 = createSummaryFeedback(summary2);
        ArrayList<SummaryFeedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedback1);
        feedbacks.add(feedback2);

        List<Summary> summaries = new ArrayList<>();
        summaries.add(summary1);
        summaries.add(summary2);
        summaries.add(summary3);
        member.setSummaries(summaries);
        //when
        Long expectedTodayWorks = 1L;
        int expectedStrikes = 2;
        int expectedTotalOpinions = 0;
        when(memberRepository.findByEmail("test1@gmail.com")).thenReturn(Optional.of(member));
        when(summaryFeedbackRepository.findSummaryFeedbacksWithSummaries(member)).thenReturn(Optional.of(feedbacks));
        Long actualTodayWorks = myPageService.getDashboardInfo("test1@gmail.com").getTodayWorks();
        int actualStrikes = myPageService.getDashboardInfo("test1@gmail.com").getStrikes();
        int actualTotalOpinions = myPageService.getDashboardInfo("test1@gmail.com").getTotalOpinions();
        System.out.println(summary1.getCreatedTime().toLocalDate());
        System.out.println(summary2.getCreatedTime().toLocalDate());
        //then
        Assertions.assertThat(actualTodayWorks).isEqualTo(expectedTodayWorks);
        Assertions.assertThat(actualStrikes).isEqualTo(expectedStrikes);
        Assertions.assertThat(actualTotalOpinions).isEqualTo(expectedTotalOpinions);
    }

    Member createMember(){
        return Member.builder()
                .email("test1@gmail.com")
                .name("tester")
                .profileImage("sample.jpg")
                .password("password")
                .isOAuth(false)
                .build();
    }
    Article createArticle(){
        Article article = new Article();
        article.setContent("기사내용입니다.");
        return article;
    }

    Summary createSummary(Member member, Article article, Status status,LocalDateTime createdTime){
        return Summary.builder()
                .id(1L)
                .takenTime(100L)
                .status(status)
                .writer(member)
                .article(article)
                .content("요약 내용입니다.")
                .createdTime(createdTime)
                .build();
    }

    SummaryFeedback createSummaryFeedback(Summary summary){
        return SummaryFeedback.builder()
                .score(Score.EXCELLENT)
                .content("굳")
                .summary(summary)
                .build();
    }
}