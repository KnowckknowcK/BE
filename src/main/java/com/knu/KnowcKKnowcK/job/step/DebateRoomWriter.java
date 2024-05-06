package com.knu.KnowcKKnowcK.job.step;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebateRoomWriter implements ItemWriter<DebateRoom> {
    private final DebateRoomRepository debateRoomRepository;

    @Override
    public void write(@NonNull Chunk<? extends DebateRoom> debateRooms){
        debateRoomRepository.saveAll(debateRooms);
    }
}
