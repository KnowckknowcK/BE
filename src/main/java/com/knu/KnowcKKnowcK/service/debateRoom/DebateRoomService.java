package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.domain.MemberDebateId;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.calculateRatio;

@Service
@RequiredArgsConstructor
public class DebateRoomService {
    private final DebateRoomRepository debateRoomRepository;
    private final MemberDebateRepository memberDebateRepository;
    public DebateRoomResponseDto participateInDebateRoom(Member member, Long debateRoomId){
        Position position;
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        Optional<MemberDebate> memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom);
        if(memberDebate.isEmpty()){ // 참여 상태가 아니라면 MemberDebate 생성하여 참여
            position = decidePosition();
            memberDebateRepository.save(buildMemberDebate(member, debateRoom, position));

            // 토론방 인원 최신화 후 저장
            if(position.equals(Position.AGREE))
                debateRoom.setAgreeNum(debateRoom.getAgreeNum() + 1);
            else debateRoom.setDisagreeNum(debateRoom.getDisagreeNum() + 1);

            debateRoomRepository.save(debateRoom);
        }

        return buildDebateRoomResponseDto(
                calculateRatio(debateRoom.getAgreeLikesNum(), debateRoom.getDisagreeLikesNum()),
                debateRoom.getAgreeNum(),
                debateRoom.getDisagreeNum());
    }

    private DebateRoomResponseDto buildDebateRoomResponseDto(double ratio, long agreeNum, long disagreeNum) {
        return DebateRoomResponseDto.builder()
                .ratio(ratio)
                .agreeNum(agreeNum)
                .disagreeNum(disagreeNum)
                .build();
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
        if(memberDebate.getPosition().equals(Position.AGREE)) // 찬성 수 감소
            debateRoom.setAgreeNum(debateRoom.getAgreeNum() - 1);
        else // 반대 수 감소
            debateRoom.setDisagreeNum(debateRoom.getDisagreeNum() - 1);
        debateRoomRepository.save(debateRoom);
        return calculateRatio(debateRoom.getAgreeNum(), debateRoom.getDisagreeNum());
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


}
