package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    //private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        //소셜 로그인 후, 세션에 저장한 사용자 정보(session dto-CustomOAuth2UserService.java) 가져옴.
        //SessionUser user = (SessionUser)httpSession.getAttribute("user");
        //위의 소스를 @LoginUser 애노테이션을 직접 생성하여 대체. @LoginUser 애노티에션이 위의 코드 역할함.
        if(user != null)    model.addAttribute("userName", user.getName());

        return "index"; //mustache파일명
        //gradle의 mustache starter의존으로 앞의 경로와 뒤의 파일확장자는 자동으로 지정된다.
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
