package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDebateRepository extends JpaRepository<MemberDebate, Long> {
    MemberDebate findByMemberIdAndDebateRoomId(Member memberId, DebateRoom debateRoomId);
}
