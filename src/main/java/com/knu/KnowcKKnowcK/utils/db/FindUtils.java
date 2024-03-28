package com.knu.KnowcKKnowcK.utils.db;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUtils {
    private final MemberRepository memberRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final PreferenceRepository preferenceRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository messageThreadRepository;

    public DebateRoom findDebateRoom(Long id){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        return debateRoom;
    }
    public MemberDebate findMemberDebate(Member member, DebateRoom debateRoom){
        return memberDebateRepository.findByMemberIdAndDebateRoomId(member, debateRoom);
    }

    public List<Message> findMessageList(DebateRoom debateRoom){
        return messageRepository.findByDebateRoomId(debateRoom);
    }
    public Member findMember(Long id){
        return memberRepository.findById(id).orElse(null);
    }

    public Preference findPreference(Member member, Message message){
        return preferenceRepository.findByMemberIdAndMessageId(member, message);

    }

    public Message findMessage(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow();
    }

    public List<MessageThread> findMessageThread(Message message){
        return messageThreadRepository.findByMessageId(message);
    }
}
