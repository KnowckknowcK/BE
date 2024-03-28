package com.knu.KnowcKKnowcK.utils.db;

import com.knu.KnowcKKnowcK.domain.MemberDebate;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUtils {
    private final MemberRepository memberRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final PreferenceRepository preferenceRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository messageThreadRepository;

    public void saveMemberDebate(MemberDebate memberDebate){
        memberDebateRepository.save(memberDebate);
    }
}
