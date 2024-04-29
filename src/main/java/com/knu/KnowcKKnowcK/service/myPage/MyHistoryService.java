package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyHistoryService {
    private final MemberRepository memberRepository;
    private final SummaryFeedbackRepository summaryFeedbackRepository;


    public List<MySummaryResponseDto> getMySummaries(Long id, Status status){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        if (status.equals(Status.ING)){
            List<Summary> summaries =  member.getSummaries().stream().filter(summary -> summary.getStatus().equals(Status.ING)).toList();
            log.info(summaries.toString());
            return summaries.stream().map(MySummaryResponseDto::new).toList();
        }
        else {
            List<Summary> summaries =  member.getSummaries().stream().filter(summary -> summary.getStatus().equals(Status.DONE)).toList();
            List<MySummaryResponseDto> mySummaryResponseDtos = new ArrayList<>();
            for (Summary summary: summaries){
                SummaryFeedback summaryFeedback = summaryFeedbackRepository.findSummaryFeedbackBySummary(summary);
                mySummaryResponseDtos.add(new MySummaryResponseDto(summary,summaryFeedback));
            }
            return mySummaryResponseDtos;
        }
    }
}
