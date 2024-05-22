package com.knu.KnowcKKnowcK.service.article;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.article.ArticleResponseDto;
import com.knu.KnowcKKnowcK.enums.Category;
import com.knu.KnowcKKnowcK.repository.ArticleRepository;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RedisUtil redisUtil;


    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getRecommendedArticles(){
        String key = LocalDate.now().toString();
        List<ArticleResponseDto> responseDtoList = redisUtil.getDataList(key, ArticleResponseDto.class);

        if (responseDtoList.isEmpty()) {
            responseDtoList = articleRepository.find1ByCategory().stream()
                    .map(ArticleResponseDto::from)
                    .collect(Collectors.toList());
            redisUtil.setDataList(key, responseDtoList);
        }

        return responseDtoList;
    }

}
