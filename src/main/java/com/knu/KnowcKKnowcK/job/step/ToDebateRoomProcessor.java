package com.knu.KnowcKKnowcK.job.step;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ToDebateRoomProcessor implements ItemProcessor<Article, DebateRoom> {
    @Override
    public DebateRoom process(Article article) throws Exception {
        return new DebateRoom(article);
    }
}
