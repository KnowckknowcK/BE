package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.requestdto.SummaryRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.SummaryFeedbackRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import com.knu.KnowcKKnowcK.service.articleSummary.SaveSummaryServiceImpl;
import com.knu.KnowcKKnowcK.service.chatGptService.SummaryFeedbackService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

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
    private SaveSummaryServiceImpl sut;


    @Mock
    private SummaryFeedbackService summaryFeedbackService;

    @Mock
    private SummaryFeedbackRepository summaryFeedbackRepository;



    @Test
    @DisplayName("요약 수동 제출에 성공하면 AI 피드백이 반환된다.")
    void saveSummary_when_not_auto() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.DONE);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(summaryRepository.save(any())).thenReturn(summary);
        Mockito.when(summaryFeedbackService.callGptApi(article.getContent(), summary.getContent())).thenReturn(Pair.of(70,"content"));
        Mockito.when(summaryFeedbackRepository.save(any())).thenReturn(new SummaryFeedback(1L, "content",70, summary));
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L, 1L,
                summary.getContent(), LocalDateTime.now(), Status.DONE, 100L);

        SummaryResponseDto summaryResponseDto = sut.saveSummary(summaryRequestDto);

        Assertions.assertThat(summaryResponseDto.getScore()).isEqualTo(70);
    }

    @Test
    @DisplayName("요약 자동 제출에 성공하면 임시저장을 한다.")
    void saveSummary_when_auto() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(summaryRepository.save(any())).thenReturn(summary);
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L, 1L,
                summary.getContent(), LocalDateTime.now(), Status.ING, 100L);

        SummaryResponseDto summaryResponseDto = sut.saveSummary(summaryRequestDto);

        Assertions.assertThat(summaryResponseDto.getReturnMessage()).isNotNull();
    }

    @Test
    @DisplayName("저장된 소요 시간이 저장할 소요 시간보다 크면 실패한다.")
    void saved_takenTime_bigger_than_now_takenTime(){
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        Mockito.when(summaryRepository.findByArticleAndWriter(article,member)).thenReturn(Optional.ofNullable(summary));
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L, 1L,
                summary.getContent(), LocalDateTime.now(), Status.ING, 50L);

        Assertions.assertThatThrownBy(()->sut.saveSummary(summaryRequestDto)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("피드백의 양식이 옳지 않으면 Exception 을 반환한다.")
    void return_exception_when_invalid_feedback() {
        Article article = createArticle();
        Member member = createMember();
        Summary summary = createSummary(member, article, Status.ING);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(summaryRepository.save(any())).thenReturn(summary);
        SummaryRequestDto summaryRequestDto = new SummaryRequestDto(1L, 1L,
                summary.getContent(), LocalDateTime.now(), Status.ING, 100L);

        SummaryResponseDto summaryResponseDto = sut.saveSummary(summaryRequestDto);

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