package com.seo.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    //블로그 메인 화면 출력//
    @GetMapping(value = {"/blog/","/blog/list"})
    public String blogList(){
        return "blog/list";
    }
}
