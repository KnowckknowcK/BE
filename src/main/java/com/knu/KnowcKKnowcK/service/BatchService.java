package com.knu.KnowcKKnowcK.service;

import com.knu.KnowcKKnowcK.job.BatchJobLauncher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchJobLauncher batchJobLauncher;
    public boolean runMakeDebateRoomBatch() throws Exception {
        return batchJobLauncher.runMakeDebateRoomJob();

    }
}
