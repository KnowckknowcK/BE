package com.knu.KnowcKKnowcK.service;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.domain.MemberDebateId;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebateRoomService {
    private final DebateRoomRepository debateRoomRepository;
    private final MemberDebateRepository memberDebateRepository;
    public String participateInDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId).orElseThrow();
        MemberDebate memberDebate = buildMemberDebate(member, debateRoom, decidePosition());
        memberDebateRepository.save(memberDebate);
        // debateRoom에 있는 score를 바꿔주기

        return "참여 성공";
    }

    public void leaveDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId).orElseThrow();
        MemberDebate memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom).orElseThrow();
        memberDebateRepository.delete(memberDebate);
        // debateRoom에 있는 score를 바꿔주기

    }
    private String decidePosition(){
        // 기존 찬/반 비율에 따라 찬/반 결정
        return "찬성";
    }
    private MemberDebate buildMemberDebate(Member member, DebateRoom debateRoom, String position){
        MemberDebateId memberDebateId = new MemberDebateId(member.getId(), debateRoom.getId());
        return MemberDebate.builder()
                .id(memberDebateId)
                .member(member)
                .debateRoom(debateRoom)
                .position(position)
                .build();
    }
}
