package com.knu.KnowcKKnowcK.dto.requestdto;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.Preference;
import com.knu.KnowcKKnowcK.domain.PreferenceId;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import lombok.Data;

@Data
public class PreferenceRequestDto {
    private Boolean isLike;

    public Preference toPreference(Member member, Message message){
        PreferenceId preferenceId = new PreferenceId(member.getId(), message.getId());
        return Preference.builder()
                .id(preferenceId)
                .message(message)
                .member(member)
                .isLike(isLike)
                .build();

    }

    public void validate(){
        if(isLike == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }
}
