package com.knu.KnowcKKnowcK.dto.oauth;

import com.knu.KnowcKKnowcK.domain.Member;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

import static com.knu.KnowcKKnowcK.service.account.AccountService.MemberPasswordGenerator.generateRandomPassword;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profileImg;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
                           String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.profileImg = picture;
    }
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .profileImage(profileImg)
                .password(generateRandomPassword(10))
                .isOAuth(true)
                .build();
    }
}

