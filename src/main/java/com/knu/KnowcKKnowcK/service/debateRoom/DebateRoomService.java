package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.domain.MemberDebateId;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.calculateRatio;

@Service
@RequiredArgsConstructor
public class DebateRoomService {
    private final DebateRoomRepository debateRoomRepository;
    private final MemberDebateRepository memberDebateRepository;
    public double participateInDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        // memberDebate 생성 후 저장(토론방 참여)
        MemberDebate memberDebate = buildMemberDebate(member, debateRoom, decidePosition());
        memberDebateRepository.save(memberDebate);

        // debateRoom에 있는 num 업데이트 및 비율 반환
        return updatePositionScore(debateRoom, memberDebate.getPosition(), true);
    }

    public double leaveDebateRoom(Member member, Long debateRoomId){
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        // memberDebate 가져온 후 삭제(토론방 나가기)
        MemberDebate memberDebate = memberDebateRepository
                .findByMemberAndDebateRoom(member, debateRoom)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        memberDebateRepository.delete(memberDebate);

        // debateRoom에 있는 num 업데이트
        return updatePositionScore(debateRoom, memberDebate.getPosition(), false);
    }
    private Position decidePosition(){
        // 기존 찬/반 비율에 따라 찬/반 결정
        return Position.AGREE;
    }
    private MemberDebate buildMemberDebate(Member member, DebateRoom debateRoom, Position position){
        MemberDebateId memberDebateId = new MemberDebateId(member.getId(), debateRoom.getId());
        return MemberDebate.builder()
                .id(memberDebateId)
                .member(member)
                .debateRoom(debateRoom)
                .position(position)
                .build();
    }

    private double updatePositionScore(DebateRoom debateRoom, Position position, boolean isParticipate){
        if(isParticipate){ // 토론방에 참여한 경우
            if(position.equals(Position.AGREE)) // 찬성 수 증가
                debateRoom.setAgreeNum(debateRoom.getAgreeNum() + 1);
            else // 반대 수 증가
                debateRoom.setDisagreeNum(debateRoom.getDisagreeNum() + 1);
        }else{ // 토론방에서 나간 경우
            if(position.equals(Position.AGREE)) // 찬성 수 감소
                debateRoom.setAgreeNum(debateRoom.getAgreeNum() - 1);
            else // 반대 수 감소
                debateRoom.setDisagreeNum(debateRoom.getDisagreeNum() - 1);
        }
        debateRoomRepository.save(debateRoom);
        return calculateRatio(debateRoom.getAgreeNum(), debateRoom.getDisagreeNum());
    }

}
