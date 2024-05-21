package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceRequestDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public abstract class DebateRoomSetUp {
    @InjectMocks
    protected MessageService messageService;
    @Mock
    protected DebateRoomRepository debateRoomRepository;

    @Mock
    protected MemberDebateRepository memberDebateRepository;

    @Mock
    protected MessageRepository messageRepository;

    @Mock
    protected PreferenceRepository preferenceRepository;

    @Mock
    protected MessageThreadRepository messageThreadRepository;


    protected Member member;
    protected DebateRoom debateRoom;
    protected MemberDebate memberDebate;
    protected MessageRequestDto messageDto;
    protected MessageThreadRequestDto threadDto;
    protected PreferenceRequestDto preferenceRequestDto;

    protected Preference preference;

    protected Long roomId;
    protected Message message;

    protected List<MessageThread> threadList;
    @BeforeEach
    protected void setUp(){
        // 테스트에 사용될 객체 초기화
        roomId = 1L;
        member = createMember();
        debateRoom = createDebateRoom();
        memberDebate = createMemberDebate(member, debateRoom);
        messageDto = createMessageRequestDto();
        message = messageDto.toMessage(member, debateRoom, memberDebate.getPosition());
        threadDto = createMessageThreadRequestDto();
        preferenceRequestDto = createPreferenceRequestDto();
        preference = preferenceRequestDto.toPreference(member, message);
        MessageThread thread = threadDto.toMessageThread(member, message, memberDebate.getPosition());
        threadList = new ArrayList<>();
        threadList.add(thread);
        List<Preference> preferenceList = new ArrayList<>();
        preferenceList.add(preference);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);

        when(debateRoomRepository.findById(roomId)).thenReturn(Optional.of(debateRoom));
        when(messageRepository.findByDebateRoom(debateRoom)).thenReturn(messages);
        when(memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom)).thenReturn(Optional.of(memberDebate));
        when(messageThreadRepository.findByMessage(message)).thenReturn(threadList);
        when(preferenceRepository.findByMessage(message)).thenReturn(preferenceList);
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));
    }
    Member createMember(){
        return Member.builder()
                .email("email@email.com")
                .name("member")
                .profileImage("image.com")
                .isOAuth(false)
                .build();
    }
    DebateRoom createDebateRoom(){
        DebateRoom debateRoom = new DebateRoom();
        debateRoom.setId(1L);
        debateRoom.setAgreeNum(0L);
        debateRoom.setAgreeLikesNum(0L);
        debateRoom.setDisagreeNum(0L);
        debateRoom.setDisagreeLikesNum(0L);
        return debateRoom;
    }
    MemberDebate createMemberDebate(Member member, DebateRoom debateRoom){
        return MemberDebate.builder()
                .debateRoom(debateRoom)
                .member(member)
                .position(Position.AGREE)
                .build();
    }

    MessageRequestDto createMessageRequestDto(){
        MessageRequestDto dto = new MessageRequestDto();
        dto.setRoomId(1L);
        dto.setContent("testing message");
        return dto;
    }

    MessageThreadRequestDto createMessageThreadRequestDto(){
        MessageThreadRequestDto dto = new MessageThreadRequestDto();
        dto.setContent("testing message Thread");
        dto.setRoomId(1L);
        return dto;
    }

    PreferenceRequestDto createPreferenceRequestDto(){
        PreferenceRequestDto dto = new PreferenceRequestDto();
        dto.setIsAgree(true);
        return dto;
    }

}
