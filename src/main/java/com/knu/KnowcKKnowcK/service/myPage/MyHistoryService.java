package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.responsedto.MyDoneSummaryResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MyIngSummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyHistoryService {
    private final MemberRepository memberRepository;
    private final OpinionRepository opinionRepository;
    private final OpinionFeedbackRepository opinionFeedbackRepository;
    private final SummaryRepository summaryRepository;
    private final SummaryFeedbackRepository summaryFeedbackRepository;

    @PostConstruct
    public void init(){
        Member member = new Member("더미1","test@gmail.com","url",false,"password");
        memberRepository.save(member);
        Summary summary = new Summary(1L,member,null, "content", LocalDateTime.now(), Status.DONE,50L);
        summaryRepository.save(summary);
        SummaryFeedback summaryFeedback = new SummaryFeedback(1L,"good", Score.EXCELLENT,summary);
        summaryFeedbackRepository.save(summaryFeedback);

        Summary summaryING = new Summary(1L,member,null, "content",LocalDateTime.now(), Status.ING,50L);
        summaryRepository.save(summaryING);
    }


    public List<?> getMySummaries(Long id, Status status){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        if (status.equals(Status.ING)){
            List<Summary> summaries =  member.getSummaries().stream().filter(summary -> summary.getStatus().equals(Status.ING)).toList();
            log.info(summaries.toString());
            return summaries.stream().map(MyIngSummaryResponseDto::new).toList();
        }
        else {
            List<Summary> summaries =  member.getSummaries().stream().filter(summary -> summary.getStatus().equals(Status.DONE)).toList();
            List<MyDoneSummaryResponseDto> myDoneSummaryResponseDtos = new ArrayList<>();
            for (Summary summary: summaries){
                SummaryFeedback summaryFeedback = summaryFeedbackRepository.findSummaryFeedbackBySummary(summary);
                myDoneSummaryResponseDtos.add(new MyDoneSummaryResponseDto(summary,summaryFeedback));
            }
            return myDoneSummaryResponseDtos;
        }
    }
}
