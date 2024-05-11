package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomResponseDto;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DebateRoomServiceTest {

    @InjectMocks
    private DebateRoomService debateRoomService;

    @Mock
    private MemberDebateRepository memberDebateRepository;

    @Mock
    private DebateRoomRepository debateRoomRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("토론방이 존재하지만, 사용자가 참여중이 아닐 때 요청 처리 테스트")
    void participateInDebateRoom_NewMember_Success() {
        // 준비
        Member member = new Member();
        Long debateRoomId = 1L;
        DebateRoom debateRoom = createDebateRoom();
        when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(debateRoom));
        when(memberDebateRepository.findByMemberAndDebateRoom(any(Member.class), any(DebateRoom.class)))
                .thenReturn(Optional.empty());

        // 실행
        DebateRoomResponseDto result = debateRoomService.participateInDebateRoom(member, debateRoomId);

        // 검증
        assertThat(result).isNotNull();
        assertThat(result.getAgreeNum() + result.getDisagreeNum()).isEqualTo(1);
        assertThat(result.getRatio()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("토론방이 존재하지 않을 때, 토론방 생성 함수가 제대로 동작하는 지 테스트")
    void getDebateRoom_NewRoom_Success() {
        // 준비
        Long debateRoomId = 1L;
        Article article = createArticle();
        when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.empty());
        when(articleRepository.findById(debateRoomId)).thenReturn(Optional.of(article));

        // 실행
        DebateRoom result = debateRoomService.getDebateRoom(debateRoomId);

        // 검증
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(article.getId());
        assertThat(result.getTitle()).isEqualTo(article.getTitle());
    }

    private DebateRoom createDebateRoom(){
        DebateRoom debateRoom = new DebateRoom();
        debateRoom.setId(1L);
        debateRoom.setAgreeNum(0L);
        debateRoom.setAgreeLikesNum(0L);
        debateRoom.setDisagreeNum(0L);
        debateRoom.setDisagreeLikesNum(0L);
        return debateRoom;
    }

    private Article createArticle(){
        Article article = new Article();
        article.setId(1L);
        article.setTitle("test title");
        return article;
    }
}
