package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MySummaryResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
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
    private final ArticleRepository articleRepository;
    private final SummaryRepository summaryRepository;

    public List<MySummaryResponseDto> getMySummaries(String email, Status status){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
//        Article article = new Article();
//        article.setContent("기사 내용임");
//        article.setTitle("기사제목임");
//        article.setId(1L);
//        articleRepository.save(article);
//        Summary s1 = new Summary(1L,member,article,"요약임", LocalDateTime.now(),Status.DONE,9L);
//        Summary s3 = new Summary(3L,member,article,"요약임", LocalDateTime.now(),Status.ING,9L);
//        Summary s4 = new Summary(4L,member,article,"요약임", LocalDateTime.now(),Status.ING,9L);
//        Summary s2 = new Summary(2L,member,article,"요약임2",LocalDateTime.now(),Status.DONE,7L);
//
//        summaryRepository.save(s1);
//        summaryRepository.save(s2);
//        summaryRepository.save(s3);
//        summaryRepository.save(s4);
//
//        summaryFeedbackRepository.save(new SummaryFeedback(1L,"굳",Score.EXCELLENT,s1));
//        summaryFeedbackRepository.save(new SummaryFeedback(2L,"낫굳",Score.FAIR,s2));
        if (member.getSummaries().isEmpty()){
            return new ArrayList<>();
        }
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
