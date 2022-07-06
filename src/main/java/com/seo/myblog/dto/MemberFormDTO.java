package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberFormDTO {
    
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email; //이메일

    @NotBlank(message = "패스워드는 필수 입력 값입니다.")
    @Length(min=8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력하세요")
    private String password;//패스워드

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nick;//닉네임
    
    private String address;//주소
}
