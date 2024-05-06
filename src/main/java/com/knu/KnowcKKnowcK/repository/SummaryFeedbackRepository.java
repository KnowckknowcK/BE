package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummaryFeedbackRepository extends JpaRepository<SummaryFeedback, Long> {
    Optional<SummaryFeedback> findSummaryFeedbackBySummary(Summary summary);
}
