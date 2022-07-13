package com.seo.myblog.controller;

import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.dto.PostFormDTO;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogController {

    //블로그 메인 화면 출력//
    @GetMapping(value = {"/blog/","/blog/list"})
    public String blogList(){
        return "/blog/list";
    }

    /*포스트 쓰기 화면 출력*/
    @GetMapping(value = {"/admin/blogPost"})
    public String postForm(Model model) {
        model.addAttribute("postFormDTO", new PostFormDTO());
        return "/blog/postForm";
    }

    /*포스트 글 쓰기 처리*/
    @PostMapping(value = {"/admin/blogPost"})
    public void postForm(PostFormDTO postFormDTO){
        System.out.println(postFormDTO.getUploadFile().isEmpty());
        System.out.println(postFormDTO.getHeadImgFile().isEmpty());
        System.out.println(postFormDTO);
    }
}
