package com.knu.KnowcKKnowcK.job.step;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToDebateRoomProcessor implements ItemProcessor<Article, DebateRoom> {
    private final DebateRoomRepository debateRoomRepository;
    @Override
    public DebateRoom process(Article article){
        boolean exists = debateRoomRepository.existsByArticleId(article.getId());
        if(exists){
            return null;
        }
        return new DebateRoom(article);
    }
}
