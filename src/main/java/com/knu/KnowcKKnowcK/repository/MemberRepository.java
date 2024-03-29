package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
