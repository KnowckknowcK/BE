package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.OpinionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionFeedbackRepository extends JpaRepository<OpinionFeedback, Long> {
}
