package com.seo.myblog.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    //블로그 메인 화면 출력//
    @GetMapping(value = {"/blog/","/blog/list"})
    public String blogList(){
        return "/blog/list";
    }

    /*포스트 쓰기 화면 출력*/
    @GetMapping(value = {"/admin/blogPost"})
    public String postForm(){
        return "/blog/postForm";
    }
}
