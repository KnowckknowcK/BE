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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberDebateRepository memberDebateRepository;
    private  final DebateRoomRepository debateRoomRepository;


    public MyPageService(MemberRepository memberRepository, MemberDebateRepository memberDebateRepository, DebateRoomRepository debateRoomRepository) {
        this.memberRepository = memberRepository;
        this.memberDebateRepository = memberDebateRepository;
        this.debateRoomRepository = debateRoomRepository;
    }

    //멤버의 프로필 정보 응답
    @Transactional
    public ProfileResponseDto getProfile(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->  new CustomException(ErrorCode.INVALID_INPUT));
        return new ProfileResponseDto(member);
    }

    //멈버의 프로필 정보 업데이트
    @Transactional
    public void updateProfile(String email, ProfileRequestDto request){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        member.updateProfile(request.getName(), request.getEmail(), request.getProfileImage());
    }

}
