package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MySummaryServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SummaryFeedbackRepository summaryFeedbackRepository;

    @InjectMocks
    private MySummaryService mySummaryService;

    @Test
    @DisplayName("사용자가 작성 중인 요약 조회")
    void getMyIngSummaries() {
        //given
        Member member = createMember();
        member.setId(1L);
        Article article = createArticle();
        Summary summary = createSummary(member,article,Status.ING);
        List<Summary> summaries = new ArrayList<>();
        summaries.add(summary);
        member.setSummaries(summaries);
        //when
        List<MySummaryResponseDto> expected = new ArrayList<>();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        expected.add(new MySummaryResponseDto(summary));
        List<MySummaryResponseDto> actual = mySummaryService.getMySummaries("test1@gmail.com", Status.ING);
        //then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("사용자가 작성 완료한 요약과 피드백 조회")
    void getMyDoneSummaries() {
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
        List<MySummaryResponseDto> expected = new ArrayList<>();
        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(summaryFeedbackRepository.findSummaryFeedbackBySummary(summary)).thenReturn(Optional.of(feedback));
        expected.add(new MySummaryResponseDto(summary,feedback));
        List<MySummaryResponseDto> actual = mySummaryService.getMySummaries("test1@gmail.com", Status.DONE);
        //then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
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

    Summary createSummary(Member member, Article article,Status status){
        return Summary.builder()
                .id(1L)
                .takenTime(100L)
                .status(status)
                .writer(member)
                .article(article)
                .content("요약 내용입니다.")
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