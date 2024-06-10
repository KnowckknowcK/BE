package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummaryFeedbackRepository extends JpaRepository<SummaryFeedback, Long> {
    Optional<SummaryFeedback> findSummaryFeedbackBySummary(Summary summary);

    @Query("SELECT sf FROM SummaryFeedback sf JOIN FETCH sf.summary " +
            "WHERE sf.summary.writer = :writer AND sf.summary.status = com.knu.KnowcKKnowcK.enums.Status.DONE")
    Optional<List<SummaryFeedback>> findSummaryFeedbacksWithSummaries(Member writer);
}