package com.knu.KnowcKKnowcK.dto.requestdto;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.Preference;
import com.knu.KnowcKKnowcK.domain.PreferenceId;
import lombok.Data;

@Data
public class PreferenceDto {
    private boolean isLike;

    public Preference toPreference(Member member, Message message){
        PreferenceId preferenceId = new PreferenceId(member.getId(), message.getId());
        return Preference.builder()
                .id(preferenceId)
                .message(message)
                .member(member)
                .isLike(isLike)
                .build();

    }
}
