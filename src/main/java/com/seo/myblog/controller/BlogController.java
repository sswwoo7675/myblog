package com.seo.myblog.controller;

import com.seo.myblog.dto.*;
import com.seo.myblog.service.CategoryService;
import com.seo.myblog.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
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

    @GetMapping(value = "/admin/editPost")
    public String postEdit(Model model, Optional<Long> postId, RedirectAttributes re){
        //postId를 받아오지 못했을경우 list로 리다이렉트
        if(postId.isEmpty()){
            re.addFlashAttribute("errMsg","잘못된 접근입니다.");
            return "redirect:/blog/list";
        }

        //postFormDTO 불러오기
        PostFormDTO postFormDTO;
        try {
            postFormDTO = postService.getPostFormDTO(postId.get());
        } catch (Exception e){
            re.addFlashAttribute("errMsg","오류로 인해 포스트 수정 페이지 접근에 실패하였습니다.");
            return "redirect:/blog/" + postId.get();
        }

        //모든 카테고리 정보 조회
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        
        //포스트 수정 페이지 반환
        model.addAttribute("postFormDTO",postFormDTO);
        model.addAttribute("categoryDTOList", categoryDTOList);
        return "/blog/postEdit";

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
    public String postDetail(@PathVariable("postId") Long postId, Model model, RedirectAttributes re){

        PostDTO postDTO;

        try {
            postDTO = postService.getPost(postId);
        } catch (EntityNotFoundException e){
            re.addFlashAttribute("errMsg","등록되지 않은 포스트 입니다.");
            return "redirect:/blog/list";
        } catch (Exception e){
            re.addFlashAttribute("errMsg","포스트를 불러오는데 실패하였습니다.");
            return "redirect:/blog/list";
        }

        model.addAttribute("postDTO", postDTO);
        return "/blog/postDetail";

    }

    /*
    * 포스트 삭제
    * */
    @DeleteMapping(value = "/blog/{postId}")
    public @ResponseBody ResponseEntity postDelete(@PathVariable("postId") Long postId, @AuthenticationPrincipal UserInfoDTO userInfoDTO){
        //비 로그인시 삭제권한 없음
        if(userInfoDTO==null){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        //로그인 사용자의 닉네임 얻어옴
        String nick = userInfoDTO.getNick();

        //로그인 사용자가 쓴 포스트 인지 검증
        try {
            if(!postService.validatePost(postId,nick)){
                return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }catch (Exception e){
            return new ResponseEntity<String>("존재하지 않는 포스트입니다", HttpStatus.NOT_FOUND);
        }

        //포스트 삭제 시도
        try {
            postService.deletePost(postId);
        } catch (Exception e){
            return new ResponseEntity<String>("포스트 삭제에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Long>(postId,HttpStatus.OK);
    }
}













