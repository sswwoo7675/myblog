package com.seo.myblog.controller;

import com.seo.myblog.dto.*;
import com.seo.myblog.service.CategoryService;
import com.seo.myblog.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;

    private final CategoryService categoryService;

    //블로그 메인 화면 출력//
    @GetMapping(value = {"/blog/","/blog/list"})
    public String blogList(Optional<Integer> page, Model model){
        //pageable 생성: 페이지 값이 없을경우 0(첫페이지), 있을경우 페이지 값 -1, size(페이지 당 자료개수)는 5개로 설정
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0,5);

        //모든 포스트 조회(페이징처리), 페이지 정보 생성
        Page<PostDTO> postDTOS = postService.getAllPost(pageable);
        PageInfo pageInfo = new PageInfo(postDTOS);

        model.addAttribute("postDTOS",postDTOS);
        model.addAttribute("pageInfo",pageInfo);
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


    /*
    * Post 상세 페이지 조회
    * */
    @GetMapping(value = "/blog/{postId}")
    public String postDetail(@PathVariable("postId") Long postId, Model model){

        PostDTO postDTO;

        try {
            postDTO = postService.getPost(postId);
        } catch (EntityNotFoundException e){
            model.addAttribute("errMsg","등록되지 않은 포스트 입니다.");
            return "/blog/list";
        } catch (Exception e){
            model.addAttribute("errMsg","포스트를 불러오는데 실패하였습니다.");
            return "/blog/list";
        }

        model.addAttribute("postDTO", postDTO);
        return "/blog/postDetail";

    }
}













