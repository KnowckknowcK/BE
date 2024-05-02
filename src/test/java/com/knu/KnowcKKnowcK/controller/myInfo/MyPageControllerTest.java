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
class MyPageControllerTest {


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
//
//    // @Test
//    @DisplayName("토론방 조회")
//    void getDebateRoom() {
//        //given
//        Member member = new Member("더미1","test@gmail.com","url",false,"password");
//        member.setId(1L);
//        Member member2 = new Member("더미2","test1@gmail.com","url",false,"password");
//        member2.setId(2L);
//        memberRepository.save(member);
//        memberRepository.save(member2);
//
//        DebateRoom debateRoom1 = new DebateRoom();
//        debateRoom1.setTitle("debateRoom1");
//        DebateRoom debateRoom2 = new DebateRoom();
//        debateRoom2.setTitle("debateRoom2");
//        debateRoomRepository.save(debateRoom1);
//        debateRoomRepository.save(debateRoom2);
//
//        MemberDebateId memberDebateId1 = new MemberDebateId(member.getId(), debateRoom1.getId());
//        MemberDebateId memberDebateId2 = new MemberDebateId(member.getId(), debateRoom2.getId());
//
//        MemberDebate memberDebate1 = new MemberDebate(memberDebateId1,member,debateRoom1, Position.AGREE);
//        MemberDebate memberDebate2 = new MemberDebate(memberDebateId2,member,debateRoom2, Position.DISAGREE);
//
//        memberDebateRepository.save(memberDebate1);
//        memberDebateRepository.save(memberDebate2);
//
//        //when
//        List<DebateRoom> expected = new ArrayList<>();
//        expected.add(debateRoom1);
//        expected.add(debateRoom2);
//
//        List<DebateRoom> actual = myPageService.getDebateRoom(member.getId());
//        List<DebateRoom> nullActual = myPageService.getDebateRoom(member2.getId());
//
//        //then
//        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
//        assertThat(nullActual).usingRecursiveComparison().isEqualTo(null);
//    }
//
//
//}
}
