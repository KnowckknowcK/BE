package com.knu.KnowcKKnowcK.controller.articleSummary;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class LoadArticlesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("존재하는 모든 지문을 목록으로 조회한다.")
    void loadArticles() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/article/list/POLITICS")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    @DisplayName("id에 맞는 지문 1개를 조회한다.")
    void loadArticleById() throws Exception{

        Long articleId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/article/{articleId}", articleId)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}