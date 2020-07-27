package com.jojoldu.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        //given
        String name = "test";
        int amount  = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name,amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
        /*
        * assertThat
        * ㄴassertj라는 테스트 검증 라이브러리의 검증 메소드.
        * ㄴ검증하고싶은 대상을 메소드 인자로 받음.
        * 메소드 체이닝이 지원됨. isEqualTo와 같이 메소드를 이어서 사용 가능.
        *
        * isEqualTo
        * ㄴassertj의 동등비교 메소드.
        * ㄴassertThat에 있는 값과 isEqualTo의 값이 같을때만 성공.
        * */
    }
}
