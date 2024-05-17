package com.knu.KnowcKKnowcK.service.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Opinion;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleListResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.OpinionRepository;
import com.knu.KnowcKKnowcK.repository.SummaryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class LoadArticlesServiceTest {

    @InjectMocks
    private LoadArticlesServiceImpl sut;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private OpinionRepository opinionRepository;

    @Test
    @DisplayName("지문 목록 조회에 성공하고 해당 기사의 요약을 진행했을 경우 summaryDone = True가 나온다.")
    void loadArticles() {
        int pageNum = 0;
        Member member = createMember();
        Article article = createArticle("기사 예시");
        Summary summary = createSummary(member, article, Status.DONE);
        Opinion opinion = createOpinion(article, member);
        Page<Article> page = createPage();
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createdTime"));
        Pageable pageable = PageRequest.of(pageNum, 5, Sort.by(sorts));
        Mockito.when(articleRepository.findByCategory(Category.CULTURE, pageable))
                .thenReturn(page);
        Mockito.when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        Mockito.when(summaryRepository.findByArticleAndWriter(any(), any())).thenReturn(Optional.of(summary));
        Mockito.when(opinionRepository.findByArticleAndWriter(any(), any())).thenReturn(Optional.of(opinion));

        Page<ArticleListResponseDto> loaded = sut.loadArticles(Category.CULTURE, pageNum, "admin");

        Assertions.assertThat(loaded.getTotalElements()).isEqualTo(7);
        Assertions.assertThat(loaded.getContent().get(0).isSummaryDone()).isEqualTo(true);
        Assertions.assertThat(loaded.getContent().get(0).isOpinionDone()).isEqualTo(true);
    }

    Article createArticle(String title){
        Article article = new Article();
        article.setTitle(title);
        article.setContent("Content");

        return article;
    }

    Page<Article> createPage(){
        List<Article> articles = new ArrayList<>();
        articles.add(createArticle("title1"));
        articles.add(createArticle("title2"));
        articles.add(createArticle("title3"));
        articles.add(createArticle("title4"));
        articles.add(createArticle("title5"));
        articles.add(createArticle("title6"));
        articles.add(createArticle("title7"));

        Page<Article> page = new PageImpl<>(articles);

        return page;
    }

    Member createMember(){
        return Member.builder()
                .email("email@email.com")
                .name("member")
                .profileImage("image.com")
                .isOAuth(false)
                .build();
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

    Opinion createOpinion(Article article, Member member){
        return Opinion.builder()
                .position(Position.AGREE)
                .article(article)
                .writer(member)
                .content("string")
                .build();
    }
}