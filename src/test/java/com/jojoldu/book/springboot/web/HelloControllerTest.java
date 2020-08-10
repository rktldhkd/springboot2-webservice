package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* @RunWith(SpringRunner.class)
* ㄴ테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자 실행.
* ㄴ여기서는 SpringRunner라는 스프링 실행자 사용. 즉, 스프링부트 테스트와 JUnit 사이에 연결자 역할.
*
* @WebMvcTest
* ㄴ 여러 스프링테스트 애노테이션 중, Web(Spring MVC)에 집중할 수 있는 애노테이션.
* ㄴ 선언할 경우 @Controller, @ControllerAdvice 등을 사용 가능.
* ㄴ 단, @Service, @Component, @Repository 등은 사용 불가.
* ㄴ 여기서는 컨트롤러만 사용하기 때문에 선언.
* */
//스프링 시큐리티 추가 후, @WebMvcTest가 작동이 잘 되지않음.
//@WebMvcTest의 스캔대상은 Controller,ControllerAdvice이며 @Repository,@Service,@Component는 스캔대상이 아님.
//따라서 SecurityConfig 생성 시, 생성에 필요한 CustomOAuth2UserService를 읽지못해 에러가 발생.
//그래서 스캔대상에서 SecurityConfig를 제거함.
//@WebMvcTest의 secure옵션은 2.1부터 Deprecated 됨.
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {
    @Autowired //스프링이 관리하는 bean 주입.
    private MockMvc mvc;
    //웹 API테스르 시, 사용. 스프링MVC테스트의 시작점. 이 클래스를 통해 HTTP GET,POST등에 대한 API 테스트 가능.

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        //MockMVC를 통해 /hello 주소로 HTTP GET 요청.
        //체이닝이 지원되어 아래와같이 여러 검증기능을 이어서 선언 가능.
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
        /*
        * .andExpect(status().isOk())
        * ㄴ mvc.perfom의 결과 검증.
        * HTTP Header의 Status 검증.
        * 우리가 흔히 알고있는 200,404,500등의 상태 검증.
        * 여기선 OK 즉, 200인지 아닌지 검증.
        *
        * .andExpect(content().string(hello));
        * ㄴ mvc.perform의 결과 검증.
        * ㄴ 응답 본문의 내용 검증.
        * ㄴ Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증.
        * ㄴ GET 요청으로 넘어온 값과 위의 hello 변수값과 일치하는지 검증.
        * */
    }//hello가_리턴된다()

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                            .param("name", name)
                            .param("amount", String.valueOf(amount))
                    ).andExpect(status().isOk())
                     .andExpect(jsonPath("$.name", is(name)))
                     .andExpect(jsonPath("$.amount", is(amount)));
        /*
        * param()
        * ㄴ API테스트시, 사용될 요청파라미터 설정.
        * ㄴ 단 값은 String형만 허용. 그래서 숫자/날짜등의 데이터는 문자열로 형변환해야한다.
        * ㄴ Test에서는 URL이동 시, 파라미터값을 주지 못하므로, 여기서는 위에 정의한 변수의 값을 param값으로 설정한 후,
        * ㄴ 그 값을 다시 위의 변수와 비교함.
        *
        * jsonPath()
        * ㄴ JSON 응답값을 필드별로 검증할 수 있는 메소드.
        * ㄴ $를 기준으로 필드명 명시.
        * ㄴ 여기서는 name,amount를 검증하니 $.name, $.amount로 검증.
        * */
    }
}
