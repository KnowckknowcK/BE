package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.summary.SummaryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SaveSummaryTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @InjectMocks
    private SummaryService sut;

    @Test
    @DisplayName("요약 임시 저장을 성공한다.")
    void saveSummary_when_auto() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(summaryRepository.save(any())).thenReturn(summary);
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L,
                summary.getContent(), LocalDateTime.now(), Status.ING, 100L);

        SummaryResponseDto summaryResponseDto = sut.saveSummary(summaryRequestDto, "email@email.com");

        Assertions.assertThat(summaryResponseDto.getReturnMessage()).isNotNull();
    }


    @Test
    @DisplayName("피드백의 양식이 옳지 않으면 Exception 을 반환한다.")
    void return_exception_when_invalid_feedback() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findByEmail(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(summaryRepository.save(any())).thenReturn(summary);
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L,
                summary.getContent(), LocalDateTime.now(), Status.ING, 100L);

        SummaryResponseDto summaryResponseDto = sut.saveSummary(summaryRequestDto, "email@email.com");

        Assertions.assertThat(summaryResponseDto.getReturnMessage()).isNotNull();
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