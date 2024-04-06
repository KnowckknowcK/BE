package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberDebateRepository extends JpaRepository<MemberDebate, Long> {
    Optional<MemberDebate> findByMemberAndDebateRoom(Member member, DebateRoom debateRoom);

    List<MemberDebate> findAllByMember(Member member);
}
