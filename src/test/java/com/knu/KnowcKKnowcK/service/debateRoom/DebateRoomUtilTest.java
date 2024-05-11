package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import java.util.List;

@SpringBootTest
public class DebateRoomUtilTest extends DebateRoomSetUp{
    @Autowired
    private DebateRoomUtil debateRoomUtil;

    @Test
    public void addMessage(){
        MessageResponseDto dto = message.toMessageResponseDto(Position.AGREE.name(),
                0, 0,
                member.getProfileImage());
        debateRoomUtil.addMessage(debateRoom.getId(), dto);
        List<Object> dtoList = debateRoomUtil.getMessages(debateRoom.getId());
        debateRoomUtil.deleteMessages(debateRoom.getId());
        assertThat(dtoList.get(0)).isEqualTo(dto);

    }
}
