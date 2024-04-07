package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MemberDebateRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final DebateRoomRepository debateRoomRepository;

    //멤버의 프로필 정보 응답
    @Transactional
    public ProfileResponseDto getProfile(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() ->  new CustomException(ErrorCode.INVALID_INPUT));
        return new ProfileResponseDto(member);
    }

    //멈버의 프로필 정보 업데이트
    @Transactional
    public void updateProfile(Long id, ProfileRequestDto request){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        member.updateProfile(request.getName(), request.getEmail(), request.getProfileImage());
    }

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
