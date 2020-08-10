package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //!=null 구문 먼저 실행하고 boolean값 변수에 할당
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        //컨트롤러 메소드의 특정 파라미터를 지원할지말지의 여부.
        return isLoginUserAnnotation && isUserClass;
    }

    //애노테이션이 할 일 지정.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //세션에 있는 사용자 정보 객체 반환.
        //컨트롤러 등에서 session의 사용자정보를 가져올때 사용하는 코드의 중복성을 줄이기 위해 만듦.
        //메소드의 파라미터에 @LoginUser 타입 변수명만 입력하면, 위의 supportParameter 실행 후,
        //해당 메소드가 맞으면 resolverArgument를 실행하여 해당 값 return.
        return httpSession.getAttribute("user");
    }
}
