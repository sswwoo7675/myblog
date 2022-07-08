package com.seo.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class testcontroller {

    @GetMapping(value = "/test1")
    public String test1(){
        return "/test/test";
    }
}
