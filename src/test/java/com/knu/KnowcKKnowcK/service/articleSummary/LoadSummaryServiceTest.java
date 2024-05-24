package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryHistoryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.summary.SummaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadSummaryServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SummaryService sut;

    @Test
    @DisplayName("진행 중(Status.ING)인 요약이 존재하면 정보를 불러온다.")
    void load_summary_when_existed() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        when(summaryRepository.findByArticleAndWriter(article, member)).thenReturn(Optional.ofNullable(summary));

        SummaryHistoryResponseDto responseDto = sut.loadSummaryHistory("email@email.com", 1L);

        assertThat(responseDto.getStatus()).isEqualTo(Status.ING);
    }

    @Test
    @DisplayName("요약을 진행한 적이 없다면 Status.NEW 로 반환한다.")
    void load_summary_when_not_existed() {
        Article article = createArticle();
        Member member = createMember();
        when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        when(summaryRepository.findByArticleAndWriter(article, member)).thenReturn(Optional.empty());

        SummaryHistoryResponseDto responseDto = sut.loadSummaryHistory("email@email.com", 1L);

        assertThat(responseDto.getStatus()).isEqualTo(Status.NEW);
        assertThat(responseDto.getContent()).isNull();
    }


    Member createMember(){
        return Member.builder()
                .email("email@email.com")
                .name("member")
                .profileImage("image.com")
                .isOAuth(false)
                .build();
    }

    Article createArticle(){
        Article article = new Article();
        article.setContent("기사내용");
        return article;
    }

    Summary createSummary(Member member, Article article, Status status){
        return Summary.builder()
                .takenTime(100L)
                .status(status)
                .writer(member)
                .article(article)
                .content("이것은 요약 내용이다.")
                .id(1L)
                .createdTime(LocalDateTime.now())
                .build();
    }
}