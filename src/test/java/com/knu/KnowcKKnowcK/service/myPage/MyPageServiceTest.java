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
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MyPageServiceTest {

    @Mock
    private MemberRepository memberRepository;
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
        Article article = createArticle();
        Summary summary = createSummary(member,article,Status.DONE);
        SummaryFeedback feedback = createSummaryFeedback(summary);
        List<Summary> summaries = new ArrayList<>();
        summaries.add(summary);
        member.setSummaries(summaries);
        //when
        Long expectedTodayWorks = 2L;
        int expectedStrikes = 1;
        int expectedTotalOpinions = 0;
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        Long actualTodayWorks = myPageService.getDashboardInfo(1L).getTodayWorks();
        int actualStrikes = myPageService.getDashboardInfo(1L).getStrikes();
        int actualTotalOpinions = myPageService.getDashboardInfo(1L).getTotalOpinions();
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

    Summary createSummary(Member member, Article article, Status status){
        return Summary.builder()
                .id(1L)
                .takenTime(100L)
                .status(status)
                .writer(member)
                .article(article)
                .content("요약 내용입니다.")
                .createdTime(LocalDateTime.now())
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