package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.MessageThread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageThreadRepository extends JpaRepository<MessageThread, Long> {
    List<MessageThread> findByMessage(Message message);
}
