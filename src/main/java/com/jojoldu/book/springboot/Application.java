package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
* @EnableJpaAuditing : JPA Auditing 활성화.(이 프로젝트에선 entity 생성/수정시간 자동생성하는데 사용됨.)
* @SpringBootApplication : 스프링부트 자동설정, 스프링 bean 읽기/생성 모두 자동으로 설정됨.
* @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트 최상단에 위치함.
* */
@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); //내장WAS 실행.
    }
}
