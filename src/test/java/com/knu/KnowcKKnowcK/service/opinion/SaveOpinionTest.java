package com.knu.KnowcKKnowcK.service.opinion;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.requestdto.OpinionRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.OpinionResponseDto;
import com.knu.KnowcKKnowcK.enums.Option;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.OpinionRepository;
import com.knu.KnowcKKnowcK.service.chatGptService.ChatGptContext;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SaveOpinionTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OpinionRepository opinionRepository;

    @InjectMocks
    private OpinionService sut;

    @Mock
    private ChatGptContext chatGptContext;

    @Test
    @DisplayName("견해 제출을 하면 피드백이 제공된다.")
    void getOpinionFeedback() {
        Member member = createMember();
        Article article = createArticle();
        Mockito.when(articleRepository.findById(any())).thenReturn(Optional.ofNullable(article));
        Mockito.when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        Mockito.when(opinionRepository.findByArticleAndWriter(any(), any())).thenReturn(Optional.empty());
        OpinionRequestDto requestDto = new OpinionRequestDto(1L, "content", LocalDateTime.now(), Position.AGREE);
        Mockito.when(chatGptContext.callGptApi(Option.OPINION, article.getContent(), requestDto.getContent())).thenReturn((Pair<Score, String>) Pair.of(Score.EXCELLENT,"아주 좋아요."));

        OpinionResponseDto opinionFeedback = sut.getOpinionFeedback(requestDto, member.getEmail());

        assertThat(opinionFeedback).isNotNull();
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