package com.jojoldu.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @interface : 어노테이션 클래스 지정. 클래스명의 어노테이션이 생성되었다고 보면 됨.
// @Target : 어노테이션이 생성될 수 있는 위치. PARAMETER : 메소드의 파라미터로 선언된 객체에서만 사용가능.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
