package com.knu.KnowcKKnowcK.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("debate-room")
public class DebateRoomController {
    @PostMapping("/{debateRoomId}")
    public ResponseEntity<String> participateInDebateRoom(@PathVariable Long debateRoomId){
        // TODO: 토론방 참여 로직: MemberDebate 수정
        return ResponseEntity.ok("참여 성공");
    }

    @DeleteMapping("/{debateRoomId}")
    public ResponseEntity<String> leaveDebateRoom(@PathVariable Long debateRoomId){
        // TODO: 토론방 나가기 로직: MemberDebate 수정
        return ResponseEntity.ok("나가기 성공");
    }
}
