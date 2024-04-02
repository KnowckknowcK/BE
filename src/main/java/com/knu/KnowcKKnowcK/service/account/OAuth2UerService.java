package com.knu.KnowcKKnowcK.service.account;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.oauth.OAuthAttributes;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UerService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);

        //다른 소셜 로그인 추가 시, 구분용
        String registrationId = request.getClientRegistration().getRegistrationId();

        String userNameAttributeName = request.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        checkMemberAndSave(OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()));

        return oAuth2User;
    }

    private Member checkMemberAndSave(OAuthAttributes attributes) {

        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> {
                    if (!entity.getIsOAuth()) {
                        throw new CustomException(ErrorCode.ALREADY_REGISTERED);
                    }
                    return entity;
                })
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }

}
