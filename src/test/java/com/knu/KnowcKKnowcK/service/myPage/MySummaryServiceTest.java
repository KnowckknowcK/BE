package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class MySummaryServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private SummaryRepository summaryRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SummaryFeedbackRepository summaryFeedbackRepository;

    @Mock
    private MySummaryService mySummaryService;

    @Test
    @DisplayName("사용자가 작성 완료한 요약과 피드백 조회")
    void getMyDoneSummaries() {
        //given
        Member member = createMember();
        member.setId(1L);
        Article article = createArticle();
        Summary summary = createSummary(member,article,Status.DONE);

        List<Summary> summaries = new ArrayList<>();
        summaries.add(summary);
        member.setSummaries(summaries);

//        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.of(member));
//        Mockito.when(summaryRepository.findByArticleAndWriter(article,member)).thenReturn(Optional.of(summary));
//        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        List<MySummaryResponseDto> expected = new ArrayList<>();
        Mockito.when(mySummaryService.getMySummaries(1L,Status.DONE)).thenReturn(expected);
        List<MySummaryResponseDto> actual = mySummaryService.getMySummaries(1L, Status.DONE);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getMyOpinions() {
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
                .takenTime(100L)
                .status(status)
                .writer(member)
                .article(article)
                .content("요약 내용입니다.")
                .build();
    }
}