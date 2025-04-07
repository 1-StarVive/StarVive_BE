package com.starbucks.starvive.user.oauth2;

import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes { 
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private SocialLoginType socialLoginType;

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if ("kakao".equalsIgnoreCase(registrationId)) {
            return ofKakao("id", attributes);
        } else if ("google".equalsIgnoreCase(registrationId)) { 
            return ofGoogle("sub", attributes);
        }
       
        throw new IllegalArgumentException("Unsupported OAuth2 provider: " + registrationId);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .socialLoginType(SocialLoginType.GOOGLE)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .socialLoginType(SocialLoginType.KAKAO)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .gender(Gender.OTHER)
                .socialLoginType(socialLoginType)
                .status(UserStatus.ACTIVE)
                .build();
    }
} 