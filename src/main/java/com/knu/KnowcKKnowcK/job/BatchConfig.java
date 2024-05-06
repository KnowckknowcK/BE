package com.knu.KnowcKKnowcK.job;


import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.job.step.DebateRoomWriter;
import com.knu.KnowcKKnowcK.job.step.ToDebateRoomProcessor;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private final DebateRoomRepository debateRoomRepository;
    @Bean
    public Job makeDebateRoomJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("makeDebateRoomJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step makeDebateRoomStep(JobRepository jobRepository,
                                   JpaPagingItemReader<Article> itemReader,
                                   PlatformTransactionManager transactionManager) {
        return new StepBuilder("makeDebateRoomStep", jobRepository)
                .<Article, DebateRoom>chunk(100, transactionManager)
                .reader(itemReader)
                .processor(new ToDebateRoomProcessor(debateRoomRepository))
                .writer(new DebateRoomWriter(debateRoomRepository))
                .build();
    }

    @Bean
    public JpaPagingItemReader<Article> itemReader(){
        return new JpaPagingItemReaderBuilder<Article>()
                .name("ArticleItemReader")
                .pageSize(100)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from Article a")
                .build();
    }
}