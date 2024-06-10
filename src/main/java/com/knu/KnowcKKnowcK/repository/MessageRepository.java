package com.knu.KnowcKKnowcK.repository;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDebateRoom(DebateRoom debateRoom);
    @Query("SELECT m, COUNT(mt), COUNT(p), m.member.profileImage " +
            "FROM Message m " +
            "LEFT JOIN m.messageThreads mt " +
            "LEFT JOIN m.preferences p " +
            "WHERE m.debateRoom = :debateRoom " +
            "GROUP BY m.id, m.member.profileImage")
    List<Object[]> findMessagesWithCounts(@Param("debateRoom") DebateRoom debateRoom);

    @Query("SELECT m FROM Message m " +
            "LEFT JOIN FETCH m.messageThreads " +
            "LEFT JOIN FETCH m.member " +
            "WHERE m.id = :id")
    Optional<Message> findByIdWithMessageThreadsAndMember(@Param("id") Long id);
}
