package com.knu.KnowcKKnowcK.utils.db;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return debateRoomRepository.findById(id).orElse(null);
    }
    public MemberDebate findMemberDebate(Member member, DebateRoom debateRoom){
        return memberDebateRepository.findByMemberIdAndDebateRoomId(member, debateRoom);
    }

    public Member findMember(Long id){
        return memberRepository.findById(id).orElse(null);
    }
}
