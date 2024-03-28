package com.knu.KnowcKKnowcK.service;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.utils.db.DeleteUtils;
import com.knu.KnowcKKnowcK.utils.db.FindUtils;
import com.knu.KnowcKKnowcK.utils.db.SaveUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebateRoomService {
    private final FindUtils findUtils;
    private final SaveUtils saveUtils;
    private final DeleteUtils deleteUtils;
    // TODO: 토론방 참여 및 나가기 로직 구현 -> 참여 시 score 변경 필요
    public String participateInDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = findUtils.findDebateRoom(debateRoomId);
        MemberDebate memberDebate = buildMemberDebate(member, debateRoom, decidePosition());
        saveUtils.saveMemberDebate(memberDebate);
        //TODO: debateRoom에 있는 score를 바꿔주기
        return "참여 성공";
    }

    public void leaveDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = findUtils.findDebateRoom(debateRoomId);
        MemberDebate memberDebate = findUtils.findMemberDebate(member, debateRoom);
        deleteUtils.deleteMemberDebate(memberDebate);
        //TODO: debateRoom에 있는 score를 바꿔주기
    }
    private String decidePosition(){
        return "찬성";
    }
    private MemberDebate buildMemberDebate(Member member, DebateRoom debateRoom, String position){
        return MemberDebate.builder()
                .debateRoomId(debateRoom)
                .memberId(member)
                .position(position)
                .build();
    }
}
