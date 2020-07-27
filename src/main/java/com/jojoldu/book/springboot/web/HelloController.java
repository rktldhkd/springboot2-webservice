package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* @RestController : 컨트롤러를 JSON 반환하는 컨트롤러로 만들어준다.
* 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 처리한 것.
* */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /*
    * @RequestParam
    * ㄴ외부에서API로 넘긴 파라미터를 가져옴.
    * ㄴ여기서는 외부에서 name이란 이름으로 넘긴 파라미터를 String형 name에 저장. amount도 마찬가지.
    * */
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
