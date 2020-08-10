package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//원래 Application.java에 있던 @EnableJpaAuditing을 따로 분리시켜서 여기서 설정.
//스프링 시큐리티 적용후, 테스트코드에서 @SpringBootApplication과 @EnableJpaAuditing를
//같이 스캔하여 테스트 실행시 에러가 발생했음. 이걸 방지하기 위함.
@Configuration
@EnableJpaAuditing
public class JpaConfig {}