package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MyDebateroomService {
    private final MemberDebateRepository memberDebateRepository;
    private final MemberRepository memberRepository;
    //참여 중인 토론방 응답
    @Transactional
    public List<DebateRoom> getDebateRoom(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.INVALID_INPUT));
        //debate room 정보 가져오기
        List<MemberDebate> memberDebates = memberDebateRepository.findAllByMember(member);
        if (memberDebates.isEmpty()) {
            return null;
        }
        return memberDebates.stream().map(MemberDebate::getDebateRoom).toList();
    }
}
