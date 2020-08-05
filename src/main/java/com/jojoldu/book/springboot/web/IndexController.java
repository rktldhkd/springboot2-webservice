package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index"; //mustache파일명
        //gradle의 mustache starter의존으로 앞의 경로와 뒤의 파일확장자는 자동으로 지정된다.
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "posts-save";
    }
}
