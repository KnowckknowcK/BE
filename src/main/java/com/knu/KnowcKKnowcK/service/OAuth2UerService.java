package com.knu.KnowcKKnowcK.service;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.oauth.OAuthAttributes;
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

        saveOrUpdateMember(OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()));

        return oAuth2User;
    }

    private Member saveOrUpdateMember(OAuthAttributes attributes) {

        Member member = memberRepository.findByEmail(attributes.getEmail())
                // 이미 존재하는 member의 경우, 이름과 프로필 이미지를 로그인 시 update
                .map(entity -> entity.update(attributes.getName(), attributes.getProfileImg()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }

}
