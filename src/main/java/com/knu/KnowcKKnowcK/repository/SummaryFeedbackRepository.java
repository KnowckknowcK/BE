package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryFeedbackRepository extends JpaRepository<SummaryFeedback, Long> {
}
