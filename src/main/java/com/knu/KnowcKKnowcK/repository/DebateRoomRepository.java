package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebateRoomRepository extends JpaRepository<DebateRoom, Long> {
    @EntityGraph(attributePaths = {"memberDebates"})
    Optional<DebateRoom> findById(Long id);
}
