package com.seo.myblog.controller;

import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.dto.PostFormDTO;
import com.seo.myblog.service.CategoryService;
import com.seo.myblog.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;

    private final CategoryService categoryService;

    //블로그 메인 화면 출력//
    @GetMapping(value = {"/blog/","/blog/list"})
    public String blogList(){
        return "/blog/list";
    }

    /*포스트 쓰기 화면 출력*/
    @GetMapping(value = {"/admin/blogPost"})
    public String postForm(Model model) {
        //모든 카테고리 정보 조회
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        model.addAttribute("postFormDTO", new PostFormDTO());
        model.addAttribute("categoryDTOList", categoryDTOList);
        return "/blog/postForm";
    }

    /*포스트 글 쓰기 처리*/
    @PostMapping(value = {"/admin/blogPost"})
    public String postForm(@Validated PostFormDTO postFormDTO,
                         BindingResult bindingResult, Model model){
        
        //필드 바인딩 에러시 에러메시지와 함께 페이지 반환
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            model.addAttribute("errMsg",fieldError.getDefaultMessage());

            //모든 카테고리 정보 조회
            List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
            model.addAttribute("categoryDTOList", categoryDTOList);
            return "/blog/postForm";
        }
        
        //포스트 쓰기 시도
        try {
            postService.addPost(postFormDTO);
        } catch (Exception e){
            //실패 시 에러메시지와 함께 페이지 반환
            model.addAttribute("errMsg", "포스트 등록에 실패하였습니다.");

            //모든 카테고리 정보 조회
            List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
            model.addAttribute("categoryDTOList", categoryDTOList);

            return "/blog/postForm";
        }
        
        //포스트 쓰기 성공할경우 블로그 메인으로 리다이렉트
        return "redirect:/blog/";
    }
}













