package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
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
public class MySummaryService {
    private final MemberRepository memberRepository;
    private final SummaryFeedbackRepository summaryFeedbackRepository;
    private final SummaryRepository summaryRepository;
    private final ArticleRepository articleRepository;

//    @PostConstruct
//    public void init(){
//        Member member = Member.builder().build();
//        member.setId(1L);
//        memberRepository.save(member);
//        Article article = new Article();
//        article.setContent("기사 내용임");
//        articleRepository.save(article);
//        Summary summary = new Summary(1L,member,article,"요약임", LocalDateTime.now(),Status.DONE,1L);
//        Summary summaryING = new Summary(2L,member,article,"요약임2", LocalDateTime.now(),Status.ING,1L);
//
//        summaryRepository.save(summary);
//        summaryRepository.save(summaryING);
//        SummaryFeedback feedback = new SummaryFeedback(1L,"굿", Score.EXCELLENT,summary);
//        summaryFeedbackRepository.save(feedback);
//    }
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
                SummaryFeedback summaryFeedback = summaryFeedbackRepository.findSummaryFeedbackBySummary(summary).orElseThrow(() -> new CustomException(ErrorCode.FEEDBACK_NOT_EXIST));
                mySummaryResponseDtos.add(new MySummaryResponseDto(summary,summaryFeedback));
            }
            return mySummaryResponseDtos;
        }
    }
}
