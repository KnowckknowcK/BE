package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.service.myPage.MyPageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MyProfileControllerTest {


        @Mock
    private MyPageService myPageService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Member member;


    @Test
    @DisplayName("사용자의 프로필 정보 조회")
    void getMyProfile() {

    }

//    @Test
//    @DisplayName("사용자의 프로필 정보 업데이트")
//    void updateMyProfile() {
//    }
//    @Test
//    @DisplayName("토론방 조회")
//    void getDebateRoom() throws Exception {
//        //given
////        member = new Member("더미1","test@gmail.com","url",false,"password");
//        member = Member.builder()
//                .email("test1@gmail.com")
//                .name("테스트")
//                .isOAuth(false)
//                .password("password")
//                .profileImage("sample.jpg").build();
//        member.setId(1L);
//
//        mockMvc.perform()
//
//    }


}