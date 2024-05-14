package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomMemberDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.calculateRatio;

@Service
@RequiredArgsConstructor
public class DebateRoomService {
    private final DebateRoomRepository debateRoomRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final ArticleRepository articleRepository;
    // 토론방 참여
    public DebateRoomResponseDto participateInDebateRoom(Member member, Long debateRoomId){
        Position position;
        DebateRoom debateRoom = getDebateRoom(debateRoomId);

        Optional<MemberDebate> memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom);
        if(memberDebate.isEmpty()){ // 참여 상태가 아니라면 MemberDebate 생성하여 참여
            position = decidePosition(calculateRatio(debateRoom.getAgreeNum(), debateRoom.getDisagreeNum()));
            memberDebateRepository.save(buildMemberDebate(member, debateRoom, position));

            // 토론방 인원 최신화 후 저장
            if(position.equals(Position.AGREE))
                debateRoom.setAgreeNum(debateRoom.getAgreeNum() + 1);
            else debateRoom.setDisagreeNum(debateRoom.getDisagreeNum() + 1);

            debateRoomRepository.save(debateRoom);
        }

        return new DebateRoomResponseDto(
                debateRoom.getAgreeNum(),
                debateRoom.getDisagreeNum(),
                debateRoom.getAgreeLikesNum(),
                debateRoom.getDisagreeLikesNum());
    }
    DebateRoom getDebateRoom(Long debateRoomId){
        Optional<DebateRoom> debateRoom = debateRoomRepository.findById(debateRoomId);
        if (debateRoom.isEmpty()){
            Article article = articleRepository
                    .findById(debateRoomId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
            DebateRoom newRoom = new DebateRoom(article);
            debateRoomRepository.save(newRoom);
            return newRoom;
        }
        else{
            return debateRoom.get();
        }
    }

    // 토론방 떠나기
    public String leaveDebateRoom(Member member, Long debateRoomId){
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

        return "나가기 성공";
    }

    public ArrayList<DebateRoomMemberDto> getDebateRoomMember(Long debateRoomId){
        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        return makeDebateRoomMemberList(memberDebateRepository.findByDebateRoom(debateRoom));
    }

    private Position decidePosition(double ratio){
        // 비율에 따른 찬/반 결정
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        return randomNumber < ratio ? Position.DISAGREE : Position.AGREE;
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

    private ArrayList<DebateRoomMemberDto> makeDebateRoomMemberList(List<MemberDebate> memberDebateList){
        ArrayList<DebateRoomMemberDto> list = new ArrayList<>();
        for (MemberDebate memberDebate: memberDebateList) {
            list.add(new DebateRoomMemberDto(memberDebate.getMember(), memberDebate.getPosition().name()));
        }
        return list;
    }
}
