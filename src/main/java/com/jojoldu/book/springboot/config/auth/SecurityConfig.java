package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity : Spring Security 섷정들을 활성화.
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //구글로그인 후 가져온 사용자의 정보들을 기반으로 가입 및 정보수정,세션저장등의 기능 지원하는 클래스.
    private final CustomOAuth2UserService customOAuth2UserService;

    //csrf().disable().headers().frameOptions().disable() : h2-console 화면사용하기 위해 해당 옵션들을 disable.
    //authorizeRequests : URL별 권한관리 설정하는 옵션의 시작점.
    //antMatchers : 권한 관리대상 지정하는 옵션.
    //anyRequest() : 설정된 값들 외의 나머지 URL들.
    //oauth2Login : OAuth2 로그인 기능에대한 여러 설정의 진입점.
    //userInfoEndpoint : OAuth2 로그인 성공 이후 사용자 정보를 가져올때의 설정.
    //userService ; 소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체 등록.
    // ㄴ 리소스서버(소셜서비스들)에서 사용자정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시.
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
