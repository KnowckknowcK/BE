package com.knu.KnowcKKnowcK.service.debateRoom;

import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MessageRepository;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getDebateRoomKey;
import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getMessageThreadKey;

@Component
@RequiredArgsConstructor
public class MessageRedisService implements ApplicationRunner {
    private final DebateRoomRepository debateRoomRepository;
    private final MessageRepository messageRepository;
    private final RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<DebateRoom> debateRoomList = debateRoomRepository.findAll();
        List<Message> messagesList = messageRepository.findAll();
        for(DebateRoom debateRoom:debateRoomList){
            String key = getDebateRoomKey(debateRoom.getId());
            redisUtil.deleteDataList(key);
        }
        for(Message message:messagesList){
            String key = getMessageThreadKey(message.getId());
            redisUtil.deleteDataList(key);
        }
    }
}
