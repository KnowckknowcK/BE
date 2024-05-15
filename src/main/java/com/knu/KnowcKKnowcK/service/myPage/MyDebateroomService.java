package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.MyDebateRoomResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MyDebateroomService {
    private final MemberDebateRepository memberDebateRepository;
    private final MemberRepository memberRepository;
    //참여 중인 토론방 응답
    @Transactional
    public List<MyDebateRoomResponseDto> getDebateRoom(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        //debate room 정보 가져오기
        List<MemberDebate> memberDebates = memberDebateRepository.findAllByMember(member);
        if (memberDebates.isEmpty()) {
            return null;
        }
        List<MyDebateRoomResponseDto> myDebateRoomResponseDtos = new ArrayList<>();
        for (MemberDebate memberDebate : memberDebates){
            DebateRoom debateRoom = memberDebate.getDebateRoom();
            myDebateRoomResponseDtos.add(new MyDebateRoomResponseDto(memberDebate.getPosition(),debateRoom));
        }

        return myDebateRoomResponseDtos;
    }
}
