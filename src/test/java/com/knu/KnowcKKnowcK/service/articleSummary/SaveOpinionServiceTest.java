package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.service.chatGptService.OpinionFeedbackService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SaveOpinionServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private OpinionRepository opinionRepository;

    @InjectMocks
    private SaveOpinionServiceImpl sut;

    @Mock
    private OpinionFeedbackService opinionFeedbackService;

    @Mock
    private OpinionFeedbackRepository opinionFeedbackRepository;

    @Test
    @DisplayName("견해 자동 제출에 성공하면 임시저장된다.")
    void saveOpinion_when_auto() {
        Article article = createArticle();
        Member member = createMember();
        Opinion opinion = createOpinion(member, article, Status.ING);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(opinionRepository.save(any())).thenReturn(opinion);
        OpinionRequestDto opinionRequestDto = new OpinionRequestDto(1L, 1L,
                opinion.getContent(), LocalDateTime.now(), Status.DONE, Position.AGREE);

        OpinionResponseDto opinionResponseDto = sut.saveOpinion(opinionRequestDto);

        Assertions.assertThat(opinionResponseDto.getReturnMessage()).isNotNull();
    }

    @Test
    @DisplayName("견해 수동 제출에 성공하면 AI 피드백이 반환된다.")
    void saveOpinion_when_not_auto() {
        Article article = createArticle();
        Member member = createMember();
        Opinion opinion = createOpinion(member, article, Status.DONE);
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(member));
        Mockito.when(opinionRepository.save(any())).thenReturn(opinion);
        Mockito.when(opinionFeedbackService.callGptApi(article.getContent(), opinion.getContent())).thenReturn(Pair.of(1,"content"));
        Mockito.when(opinionFeedbackRepository.save(any())).thenReturn(new OpinionFeedback(1L, "content", opinion));
        OpinionRequestDto opinionRequestDto = new OpinionRequestDto(1L, 1L,
                opinion.getContent(), LocalDateTime.now(), Status.DONE, Position.AGREE);

        OpinionResponseDto opinionResponseDto = sut.saveOpinion(opinionRequestDto);

        Assertions.assertThat(opinionResponseDto.getContent()).isEqualTo("content");
        Assertions.assertThat(opinionResponseDto.getId()).isNotNull();
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

    Opinion createOpinion(Member member, Article article, Status status){
        return Opinion.builder()
                .position(Position.AGREE)
                .status(status)
                .writer(member)
                .article(article)
                .content("이것은 요약 내용이다.")
                .id(1L)
                .createdTime(LocalDateTime.now())
                .build();
    }
}