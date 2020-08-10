package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스 구분 코드. 구글로그인인가,네이버로그인인가. 소셜로그인이 하나라면 불필요.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행시 키가 되는 필드값. pk와 같은 의미.
        //구글의 경우 기본적으로 코드를 제공, 네이버/카카오등은 기본 지원안함. 구글의 기본코드는 "sub"
        //네이버로그인과 구글 로그인 동시지원할 때 사용.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스.
        //OAuth2UserService : 스프링에 구현되있는 클래스
        //네이버 등 다른 소셜 로그인도 이 클래스 사용.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser : 세션에 사용자 정보를 저장하기위한 DTO 클래스. 세션dto
        //세션에 저장하기 위해선 직렬화를 구현해야하기때문에 엔티티가 아닌 DTO 클래스를 따로만들어 세션에 저장.
        //엔티티클래스는 언제 다른엔티티와 관계가 형성될지 모르므로 직렬화 코드 추가로 발생할 성능이슈/부수효과 발생을
        // 방지하기위해 직렬화기능을 가진 세션dto를 하나 추가.
        //indexController에서 사용자 정보를 출력하기 위해 접근한다.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(
                    new SimpleGrantedAuthority(
                        user.getRoleKey())),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey()
        );
    }

    //구글 사용자 정보가 업데이트되었을때를 대비하여 update기능도 같이 구현.
    //사용자 이름이나 사진이 변경되면 User 엔티티에 반영됨.
    //OAuthAttributes 클래스 생성이 끝났으면 같은 패키지의 SessionUser 클래스를 생성한다.
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
