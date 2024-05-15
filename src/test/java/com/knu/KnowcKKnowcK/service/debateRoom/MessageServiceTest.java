package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.PreferenceResponseDto;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getDebateRoomKey;
import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getMessageThreadKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class MessageServiceTest extends DebateRoomSetUp{

    @Mock
    private RedisUtil redisUtil;

    @Test
    @DisplayName("토론방 메세지를 적절한 형태로 가공하여 보내는 지 검증하는 테스트")
    void saveAndReturnMessage() {
        // 메소드 실행
        MessageResponseDto result = messageService.saveAndReturnMessage(member, messageDto);

        // 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getRoomId()).isEqualTo(debateRoom.getId());
        assertThat(result.getContent()).isEqualTo(messageDto.getContent());
        verify(redisUtil, times(1)).addDataToList(anyString(), any(MessageResponseDto.class));
    }

    @Test
    @DisplayName("토론방 메세지 스레드를 적절한 형태로 가공하여 보내는 지 검증하는 테스트")
    void saveAndReturnMessageThread() {
        // 메소드 실행
        MessageThreadResponseDto result = messageService.saveAndReturnMessageThread(member, message.getId(),threadDto);

        // 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getProfileImage()).isEqualTo(member.getProfileImage());
        assertThat(result.getContent()).isEqualTo(threadDto.getContent());
        assertThat(result.getPosition()).isEqualTo(memberDebate.getPosition().name());
        verify(redisUtil, times(1)).addDataToList(anyString(), any(MessageThreadResponseDto.class));
    }
    @Test
    @DisplayName("메세지 리스트를 제대로 받아오는 지 검증하는 테스트")
    public void getMessagesSuccess() {
        // 메소드 실행
        List<MessageResponseDto> result = messageService.getMessages(member, roomId);

        // 검증
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getContent()).isEqualTo(message.getContent());
        verify(redisUtil, times(1)).getDataList(getDebateRoomKey(debateRoom.getId()), MessageResponseDto.class);
    }
    @Test
    @DisplayName("메세지 스레드 리스트를 제대로 받아오는 지 검증하는 테스트")
    public void getMessageThreadDtoList() {
        // 메소드 실행
        List<MessageThreadResponseDto> result = messageService.getMessageThreadDtoList(message.getId());

        // 검증
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getContent()).isEqualTo(threadDto.getContent());
        verify(redisUtil, times(1)).getDataList(getMessageThreadKey(message.getId()), MessageThreadResponseDto.class);
    }

    @Test
    @DisplayName("메세지에 대한 좋아요 표시가 제대로 되는 지 검증하는 테스트")
    void putPreference() {
        when(debateRoomRepository.save(debateRoom)).thenReturn(debateRoom);
        when(preferenceRepository.save(preference)).thenReturn(preference);
        // 유효한 입력에 대한 테스트

        PreferenceResponseDto result = messageService.putPreference(member, message.getId(), preferenceRequestDto);

    }

}
