//package com.knu.KnowcKKnowcK.controller.articleSummary;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class SaveSummaryControllerTest {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void saveSummary() throws Exception{
//
//        String request = "{\n" +
//                "  \"articleId\": 1,\n" +
//                "  \"writerId\": 2,\n" +
//                "  \"content\": \"이 문서는 더미 데이터입니다.\",\n" +
//                "  \"createdTime\": \"2023-05-15T10:30:00\",\n" +
//                "  \"status\": \"PUBLISHED\",\n" +
//                "  \"takenTime\": 30\n" +
//                "}\n";
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/summary/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print());
//
//    }
//}