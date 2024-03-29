package com.knu.KnowcKKnowcK.dto.requestdto;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.domain.Preference;
import lombok.Data;

@Data
public class PreferenceDTO {
    private boolean isLike;

    public Preference toPreference(Member member, Message message){
        return Preference.builder()
                .message(message)
                .member(member)
                .isLike(isLike)
                .build();

    }
}
