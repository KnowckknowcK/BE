package com.knu.KnowcKKnowcK.controller.articleSummary;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class SaveSummaryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @Test
    @Transactional
    @DisplayName("요약 자동 제출에 성공하면 200, 실패하면 400을 반환한다.")
    void saveSummary() throws Exception {

        //given
        Member member = new Member();
        member.setEmail("example@example.com");
        member.setPassword("password123");
        member.setProfileImage("profile.jpg");
        member.setName("John Doe");
        member.setPoint(100L);
        member.setLevel(1L);
        member.setIsOAuth(false);

        memberRepository.save(member);

        Article article = new Article();
        article.setCategory(Category.ECONOMICS);
        article.setTitle("제목");
        article.setContent("내용");
        article.setCreatedTime(LocalDateTime.now());
        article.setArticleUrl("http://example.com");

        articleRepository.save(article);

        String successJson = "{\n" +
                "\"articleId\" : 1,\n"+
                "\"writerId\" : 1,\n"+
                "  \"content\": \"이것은 요약 내용입니다.\",\n" +
                "  \"takenTime\" : 30,\n" +
                "  \"status\": \"ING\"\n" +
                "}\n";

        String failedJson = "{\n" +
                "\"articleId\" : null,\n"+
                "\"writerId\" : 1,\n"+
                "  \"content\": \"\",\n" +
                "  \"takenTime\": -1,\n" +
                "  \"status\": \"INVALID_STATUS\"\n" +
                "}\n";

        //성공 테스트 케이스
        mockMvc.perform(MockMvcRequestBuilders.post("/api/summary/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(successJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.returnMessage").value("임시 저장이 완료되었습니다."))
                        .andDo(print());


        //실패 테스트 케이스
        mockMvc.perform(MockMvcRequestBuilders.post("/api/summary/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(failedJson))
                .andExpect(MockMvcResultMatchers.status().is(ErrorCode.INVALID_INPUT.getStatus()))
                .andDo(print());
    }
}