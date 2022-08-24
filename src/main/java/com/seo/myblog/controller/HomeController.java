package com.seo.myblog.controller;

import com.seo.myblog.dto.MainPageDTO;
import com.seo.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String main(Model model){
        Pageable pageable = PageRequest.of(0,3);

        List<MainPageDTO> results = postService.loadPostByMainPage(pageable);
        model.addAttribute("posts",results);

        return "/main";
    }
}
