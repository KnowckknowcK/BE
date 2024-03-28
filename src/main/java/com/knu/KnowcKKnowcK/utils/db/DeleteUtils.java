package com.knu.KnowcKKnowcK.utils.db;

import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteUtils {
    private final MemberRepository memberRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final PreferenceRepository preferenceRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository messageThreadRepository;

    @Transactional
    public void deleteMemberDebate(MemberDebate memberDebate){
        memberDebateRepository.delete(memberDebate);
    }
}
