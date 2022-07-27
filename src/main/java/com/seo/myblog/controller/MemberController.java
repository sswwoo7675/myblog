package com.seo.myblog.controller;

import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.entity.Member;
import com.seo.myblog.exception.DuplicateMemberException;
import com.seo.myblog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /* 회원가입 화면 표출 메서드*/
    @GetMapping("/new")
    public String MemberForm(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO()); //빈 MemberFormDTO객체 생성하여 모델에 담음

        return "/member/memberForm";
    }

    /*회원가입 요청 처리 메서드*/
    @PostMapping(value = "/new")
    public String MemberForm(@Validated MemberFormDTO memberFormDTO,
                             BindingResult bindingResult, Model model,
                             RedirectAttributes re){
        if(bindingResult.hasErrors()){
            return "/member/memberForm"; //필드값 바인딩 에러 시 페이지 다시반환
        }

        try {
            memberService.register(memberFormDTO); //회원가입 시도
        } catch (DuplicateMemberException e){ //가입시도시 중복회원 오류 발생 시 에러 메시지 모델에 담고 반환
            model.addAttribute("errMsg", e.getMessage());
            return "/member/memberForm";
        }

        re.addFlashAttribute("msg","회원가입에 성공하였습니다.");
        return "redirect:/blog/"; //가입성공시 블로그 메인 화면 리다이렉트
    }

    /*로그인 화면*/
    @GetMapping(value = "/login")
    public String memberLoginForm(){
        return "/member/memberLoginForm";
    }
    
    /*로그인 실패시 처리*/
    @GetMapping(value = "/login/error")
    public String memberLoginError(Model model){
        model.addAttribute("errMsg", "아이디나 비밀번호가 올바르지 않습니다.");
        return "/member/memberLoginForm"; //에러메시지 담아서 로그인 화면 반환
    }

    /*oauth 로그인 실패시 처리*/
    @GetMapping("/member/oauth/error")
    public String memberOathLoginError(RedirectAttributes re){
        re.addFlashAttribute("msg","이 계정으로는 이용할 수 없습니다. 다른 계정을 이용하십시오.");
        return "redirect:/blog/"; 
    }

}
