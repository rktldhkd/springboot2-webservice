package com.jojoldu.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //소셜로그인으로 반환되는 값 중 email을 통해 이미 사용자인지 처음 가입자인지 판단하기위한 함수.
    //Optional 클래스 : 모든 타입의 참조변수 저장 가능.복잡한 반복문 없이도 NullPointerException 예외처리 가능.
    Optional<User> findByEmail(String email);
}
