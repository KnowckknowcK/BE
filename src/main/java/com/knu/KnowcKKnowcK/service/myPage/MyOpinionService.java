package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Opinion;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.enums.Position;
import com.knu.KnowcKKnowcK.enums.Status;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.OpinionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyOpinionService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final OpinionRepository opinionRepository;
//    @PostConstruct
//    public void init(){
//        Member member = Member.builder().build();
//        member.setId(1L);
//        memberRepository.save(member);
//        Article article = new Article();
//        article.setContent("기사 내용임");
//        articleRepository.save(article);
//        Opinion opinion = new Opinion(1L,member,article,"견해임", Position.AGREE, Status.DONE,LocalDateTime.now(),"굳");
//        Opinion opinion2 = new Opinion(2L,member,article,"요약임2", Position.DISAGREE,Status.DONE,LocalDateTime.now(),"낫굳");
//
//        opinionRepository.save(opinion);
//        opinionRepository.save(opinion2);
//    }
    public List<MyOpinionResponseDto> getMyOpinions(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Article article = new Article();
        article.setContent("기사 내용임");
        articleRepository.save(article);
        Opinion opinion = new Opinion(1L,member,article,"견해임", Position.AGREE, Status.DONE,LocalDateTime.now(),"굳");
        Opinion opinion2 = new Opinion(2L,member,article,"요약임2", Position.DISAGREE,Status.DONE,LocalDateTime.now(),"낫굳");

        opinionRepository.save(opinion);
        opinionRepository.save(opinion2);
        List<Opinion> opinions = member.getOpinions().stream().toList();
        log.info("여기"+ opinions);
        return opinions.stream().map(MyOpinionResponseDto::new).toList();
    }
}
