package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Preference findByMemberIdAndMessageId(Member memberId, Message messageId);
}
