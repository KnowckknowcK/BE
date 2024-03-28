package com.knu.KnowcKKnowcK.service;


import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDTO;
import com.knu.KnowcKKnowcK.utils.db.DeleteUtils;
import com.knu.KnowcKKnowcK.utils.db.FindUtils;
import com.knu.KnowcKKnowcK.utils.db.SaveUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final FindUtils findUtils;
    private final SaveUtils saveUtils;
    private final DeleteUtils deleteUtils;

    public void saveMessage(MessageRequestDTO messageRequestDTO){
        DebateRoom debateRoom = findUtils.findDebateRoom(messageRequestDTO.getRoomId());
        saveUtils.saveMessage(messageRequestDTO.toMessage(debateRoom));
    }
    public void saveMessageThread(Member member, Long messageId, MessageThreadRequestDTO messageThreadRequestDTO) {
        Message message = findUtils.findMessage(messageId);
        saveUtils.saveMessageThread(messageThreadRequestDTO.toMessageThread(member, message));
    }

    public List<MessageResponseDTO> getMessages(Long roomId){
        DebateRoom debateRoom = findUtils.findDebateRoom(roomId);
        List<Message> messageList = findUtils.findMessageList(debateRoom);

        List<MessageResponseDTO> messageResponseDTOList = new ArrayList<>();
        for (Message message: messageList) {
            messageResponseDTOList.add(message.toMessageDTO());
        }

        return messageResponseDTOList;
    }

    public List<MessageThreadResponseDTO> getMessageThreadDTOList(Long messageId){
        Message message = findUtils.findMessage(messageId);
        List<MessageThread> messageThraedList = findUtils.findMessageThread(message);

        List<MessageThreadResponseDTO> messageThreadResponseDTOList = new ArrayList<>();
        for (MessageThread messageThread: messageThraedList) {
            messageThreadResponseDTOList.add(messageThread.toMessageThreadDTO(messageId));
        }
        return messageThreadResponseDTOList;
    }

    public String putPreference(Member member, Long messageId, PreferenceDTO preferenceDTO){
        updatePreference(member, messageId, preferenceDTO);
        // 토론방 내부 ratio 변경 필요
        return "success!!";
    }

    private void updatePreference(Member member, Long messageId, PreferenceDTO preferenceDTO){
        Message message = findUtils.findMessage(messageId);
        Preference preference = findUtils.findPreference(member, message);
        // 현재 preference 상태에 따라 적절히 처리
        if(preference == null){ // null 일 경우 생성 후 저장
            preference = preferenceDTO.toPreference(member, message);
            saveUtils.savePreference(preference);
        } // Preference 가 이미 같은 상태로 있는 경우 삭제
        else if(preference.isLike() == preferenceDTO.isLike()){
            deleteUtils.deletePreference(preference);
        } else { // preference 가 반대의 상태라면 바꾸고 저장
            preference.setLike(preferenceDTO.isLike());
            saveUtils.savePreference(preference);
        }
    }


}
