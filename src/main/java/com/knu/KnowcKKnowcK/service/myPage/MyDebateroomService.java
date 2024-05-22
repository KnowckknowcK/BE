package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.MyDebateRoomResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
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
    private final ArticleRepository articleRepository;
    private final DebateRoomRepository debateRoomRepository;
//    참여 중인 토론방 응답
//    @PostConstruct
//    public void init(){
//        DebateRoom debateRoom1 = DebateRoom.builder()
//                .article(articleRepository.findById(1L).orElseThrow())
//                .build();
//        DebateRoom debateRoom2 = DebateRoom.builder()
//                .article(articleRepository.findById(2L).orElseThrow())
//                .build();
//        DebateRoom debateRoom3 = DebateRoom.builder()
//                .article(articleRepository.findById(3L).orElseThrow())
//                .build();
//        DebateRoom debateRoom4 = DebateRoom.builder()
//                .article(articleRepository.findById(4L).orElseThrow())
//                .build();
//        DebateRoom debateRoom5 = DebateRoom.builder()
//                .article(articleRepository.findById(5L).orElseThrow())
//                .build();
//        debateRoomRepository.save(debateRoom1);
//        debateRoomRepository.save(debateRoom2);
//        debateRoomRepository.save(debateRoom3);
//        debateRoomRepository.save(debateRoom4);
//        debateRoomRepository.save(debateRoom5);
//        MemberDebate memberDebate1 = MemberDebate.builder()
//                .id(new MemberDebateId(2L,1L))
//                .debateRoom(debateRoom1)
//                .member(memberRepository.findById(2L).orElseThrow())
//                .position(Position.AGREE).build();
//        MemberDebate memberDebate2 = MemberDebate.builder()
//                .id(new MemberDebateId(2L,2L))
//                .debateRoom(debateRoom2)
//                .member(memberRepository.findById(2L).orElseThrow())
//                .position(Position.AGREE).build();
//        MemberDebate memberDebate3 = MemberDebate.builder()
//                .id(new MemberDebateId(2L,3L))
//                .debateRoom(debateRoom3)
//                .member(memberRepository.findById(2L).orElseThrow())
//                .position(Position.AGREE).build();
//        MemberDebate memberDebate4 = MemberDebate.builder()
//                .id(new MemberDebateId(2L,4L))
//                .debateRoom(debateRoom4)
//                .member(memberRepository.findById(2L).orElseThrow())
//                .position(Position.AGREE).build();
//        MemberDebate memberDebate5 = MemberDebate.builder()
//                .id(new MemberDebateId(2L,5L))
//                .debateRoom(debateRoom5)
//                .member(memberRepository.findById(2L).orElseThrow())
//                .position(Position.AGREE).build();
//        memberDebateRepository.save(memberDebate1);
//        memberDebateRepository.save(memberDebate2);
//        memberDebateRepository.save(memberDebate3);
//        memberDebateRepository.save(memberDebate4);
//        memberDebateRepository.save(memberDebate5);
//
//    }
    @Transactional
    public List<MyDebateRoomResponseDto> getDebateRoom(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        //debate room 정보 가져오기
        List<MemberDebate> memberDebates = memberDebateRepository.findAllByMember(member);
        if (memberDebates.isEmpty()) {
            return new ArrayList<>();
        }
        List<MyDebateRoomResponseDto> myDebateRoomResponseDtos = new ArrayList<>();
        for (MemberDebate memberDebate : memberDebates){
            DebateRoom debateRoom = memberDebate.getDebateRoom();
            myDebateRoomResponseDtos.add(new MyDebateRoomResponseDto(memberDebate.getPosition(),debateRoom));
        }

        return myDebateRoomResponseDtos;
    }
}
