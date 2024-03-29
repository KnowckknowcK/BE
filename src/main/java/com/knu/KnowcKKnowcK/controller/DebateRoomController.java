package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.DebateRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/debate-room")
@RequiredArgsConstructor
@Tag(name="Debate Room", description = "토론방 참여/나가기와 관련된 API Controller")
public class DebateRoomController {
    final private DebateRoomService debateRoomService;
    private final MemberRepository memberRepository;
    private Member member; // 로그인 기능 추가되면 수정 예정

    @PostMapping("/{debateRoomId}")
    @Operation(summary = "토론방 참여 API", description = "클라이언트가 토론방에 참여하길 바랄 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "참여하길 바라는 토론방 ID", example = "3")})
    public ResponseEntity<String> participateInDebateRoom(@PathVariable Long debateRoomId){
        member = memberRepository.findById(1L).orElse(null);
        return ResponseEntity.ok(debateRoomService.participateInDebateRoom(member, debateRoomId));
    }

    @DeleteMapping("/{debateRoomId}")
    @Operation(summary = "토론방 나가기 API", description = "클라이언트가 토론방에서 나가길 바랄 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "나가길 원하는 토론방 ID", example = "3")})
    public ResponseEntity<String> leaveDebateRoom(@PathVariable Long debateRoomId){
        member = memberRepository.findById(1L).orElse(null);
        debateRoomService.leaveDebateRoom(member, debateRoomId);
        return ResponseEntity.ok("나가기 성공");
    }
}
