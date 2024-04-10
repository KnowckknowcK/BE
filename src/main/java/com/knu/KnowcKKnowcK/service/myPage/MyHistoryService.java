package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Summary;
import com.knu.KnowcKKnowcK.domain.SummaryFeedback;
import com.knu.KnowcKKnowcK.dto.responsedto.MyIngSummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Score;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
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
    public List<MyIngSummaryResponseDto> getMySummaries(Long id, Status status){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        List<Summary> summaries =  member.getSummaries().stream().toList();
        return summaries.stream().map(MyIngSummaryResponseDto::new).toList();
//        if (status.equals(Status.DONE)){
//            //요약과 피드백을 함께 전송해야
//        }
//        else {
//            //summary에서 필요한 내용만 뽑아야됨
//
//        }
    }

}
