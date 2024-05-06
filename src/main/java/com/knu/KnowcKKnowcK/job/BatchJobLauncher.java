package com.knu.KnowcKKnowcK.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class BatchJobLauncher implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    private final Job makeDebateRoomJob; // 여기서 job은 실행하고자 하는 Spring Batch 작업의 빈입니다.

    @Override
    public void run(String... args) throws Exception {
        // 현재 시간을 파라미터로 추가
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("currentTime", new Date())
                .toJobParameters();

        // 작업 실행
        jobLauncher.run(makeDebateRoomJob, jobParameters);
    }
}
