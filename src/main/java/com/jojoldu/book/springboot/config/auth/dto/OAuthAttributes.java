package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }//end builder

    //OAuth2User에서 반환하는 사용자정보는 Map이기 때문에 값 하나하나를 변환해야한다.
    //소셜 로그인 종류에 따른 해당 소셜의 사용자 정보 반환.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String,Object> attributes){
        //registrationId : 현재 로그인 진행중인 소셜 서비스 구분값(구글/네이버등)
        //userNameAttributeName : OAuth2 로그인 진행시 키가 되는 필드값.(pk) 구글만 기본값제공(sub). 나머지는 기본값없음.

        if("naver".equals(registrationId))  return ofNaver("id", attributes);
        if("kakao".equals(registrationId))  return ofKakao("id", attributes);

        return ofGoogle(userNameAttributeName, attributes);
    }
    
    //구글 로그인
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //네이버 로그인
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
        //naver로그인 성공시 가져오는 JSON데이터들 중, response 항목의 값. response항목은 사용자 정보를 가지고 있다.
        Map<String,Object> response = (Map<String, Object>)attributes.get("response");
        return OAuthAttributes.builder()
                    .name((String)response.get("name"))
                    .email((String)response.get("email"))
                    .picture((String)response.get("picture"))
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
    }

    //카카오 로그인
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes){
//        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
//            System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
//        }

        //kakao로그인 성공시 가져오는 JSON데이터들 중, kakao_account 항목의 값. kakao_account항목은 사용자 정보를 가지고 있다.
        Map<String,Object> kakao_account = (Map<String,Object>)attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String,Object>)kakao_account.get("profile");

        //카카오는 최상위에 id, kakao_account json값 항목이 따로있다. id값까지 보내려면 최상위객체에서 뽑아와야함.
        kakao_account.put("id", attributes.get("id"));

        //사진은 없어서 picture에 그냥 nickname이나 넣어줌.
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)kakao_account.get("email"))
                .picture((String)profile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //User엔티티 생성
    //OAuthAttributes에서 엔티티 생성하는 시점은 처음 가입할때.
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
