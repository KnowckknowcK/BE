package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.MemberDebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberDebateRepository extends JpaRepository<MemberDebate, Long> {
    Optional<MemberDebate> findByMemberAndDebateRoom(Member member, DebateRoom debateRoom);

    List<MemberDebate> findAllByMember(Member member);

    @Query("SELECT md FROM MemberDebate md WHERE md.member.id = :memberId AND md.debateRoom.id = :debateRoomId")
    Optional<MemberDebate> findByMemberIdAndDebateRoomId(@Param("memberId") Long memberId, @Param("debateRoomId") Long debateRoomId);
}
