package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//일종의 dao. Entity객체가 DB에 접근하기 위한 DB Layer 접근자.
//단순히 repository 인터페이스 생성 후, JpaRepository<Entity클래스명, PK타입> 만 상속하면 기본적인 CRUD메소드가 자동생성된다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
