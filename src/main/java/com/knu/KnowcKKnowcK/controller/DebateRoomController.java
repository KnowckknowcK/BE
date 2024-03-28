package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.DebateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/debate-room")
@RequiredArgsConstructor
public class DebateRoomController {
    final private DebateRoomService debateRoomService;
    private final MemberRepository memberRepository;
    private Member member;
    @PostMapping("/{debateRoomId}")
    public ResponseEntity<String> participateInDebateRoom(@PathVariable Long debateRoomId){
        member = memberRepository.findById(1L).orElse(null);
        return ResponseEntity.ok(debateRoomService.participateInDebateRoom(member, debateRoomId));
    }

    @DeleteMapping("/{debateRoomId}")
    public ResponseEntity<String> leaveDebateRoom(@PathVariable Long debateRoomId){
        member = memberRepository.findById(1L).orElse(null);
        debateRoomService.leaveDebateRoom(member, debateRoomId);
        return ResponseEntity.ok("나가기 성공");
    }
}
