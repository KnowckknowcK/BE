package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Opinion;
import com.knu.KnowcKKnowcK.dto.responsedto.MyOpinionResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.repository.OpinionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyOpinionService {
    private final MemberRepository memberRepository;
    public List<MyOpinionResponseDto> getMyOpinions(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<Opinion> opinions = member.getOpinions().stream().toList();
        return opinions.stream().map(MyOpinionResponseDto::new).toList();
    }

    public MyOpinionResponseDto getMySingleOpinion(String email, Long articleId){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        log.info(articleId.toString());
        Opinion opinion = member.getOpinions().stream()
                .filter(o -> o.getArticle().getId().equals(articleId))
                .findFirst().orElse(null);
        if (opinion == null)
            return null;
        return new MyOpinionResponseDto(opinion);
    }
}
