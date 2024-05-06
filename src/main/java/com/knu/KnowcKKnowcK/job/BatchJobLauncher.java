package com.knu.KnowcKKnowcK.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class BatchJobLauncher{
    private final JobLauncher jobLauncher;
    private final Job makeDebateRoomJob;

    // 오전 3시 30분마다 batch
    @Scheduled(cron = "0 30 3 * * *")
    public void scheduledRun() throws Exception {
        // 현재 시간을 파라미터로 추가
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("currentTime", new Date())
                .toJobParameters();

        // 작업 실행
        jobLauncher.run(makeDebateRoomJob, jobParameters);
    }
}
