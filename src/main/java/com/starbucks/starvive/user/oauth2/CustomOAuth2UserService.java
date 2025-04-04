package com.starbucks.starvive.user.oauth2;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

// 소셜 로그인 제공자로부터 사용자 정보를 성공적으로 받아온 직후에 사용되는 클래스
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    
    // 유저 정보를 기반으로 OAuth2User 객체를 생성하고 반환
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());

        if (attributes.getEmail() == null) {
            log.error("Email not found from OAuth2 provider: {}", registrationId);
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        User user = getOrRegisterUser(attributes);
        log.info("Loaded/Registered user {} via {}", user.getEmail(), registrationId);

        return oAuth2User; 
    }

    // 존재하는 유저라면 존재하는 유저를 반환하고, 존재하지 않는 유저라면 새로 생성한 후 등록하고 반환  
    private User getOrRegisterUser(OAuthAttributes attributes) {

        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            log.info("Existing user found: {}", attributes.getEmail());
        } else {
            log.info("New user registration: {}", attributes.getEmail());
            user = attributes.toEntity(); 
            userRepository.save(user);
            log.info("New user saved with ID: {}", user.getUserId());
        }
        return user;
    }
} 