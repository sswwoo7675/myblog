package com.seo.myblog.service;

import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.entity.Member;
import com.seo.myblog.exception.DuplicateMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //테스트용 Member 생성
    public Member createMember(){
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setEmail("test@test.com");
        memberFormDTO.setNick("test1");
        memberFormDTO.setAddress("테스트시 테스트동");
        memberFormDTO.setPassword("1234");
        return Member.createMember(memberFormDTO, passwordEncoder);
    }


    @Test
    @DisplayName("회원가입 테스트(정상케이스)")
    void registerSuccess() {
        //test용 MemberDTO 생성
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setEmail("test@test.com");
        memberFormDTO.setNick("test1");
        memberFormDTO.setAddress("테스트시 테스트동");
        memberFormDTO.setPassword("1234");

        try{ 
            memberService.register(memberFormDTO); //회원가입 진행
        } catch (Exception e){
            e.getMessage();
        }

        Member savedMember = memberRepository.findByEmailAndIsSocial(memberFormDTO.getEmail(),false).get(); //검증위해 저장된 Member객체 불러옴

        Assertions.assertEquals(memberFormDTO.getNick(), savedMember.getNick()); //닉네임이 같은지 비교

    }

    @Test
    @DisplayName("회원가입 테스트(중복회원가입)")
    void registerFail(){
        memberRepository.save(createMember()); //테스트용 멤버 db 저장

        //test용 MemberDTO 생성(이메일 같은 경우)
        MemberFormDTO memberFormDTO1 = new MemberFormDTO();
        memberFormDTO1.setEmail("test@test.com");
        memberFormDTO1.setNick("test2");
        memberFormDTO1.setAddress("테스트시 테스트동");
        memberFormDTO1.setPassword("1234");

        //test용 MemberDTO 생성(닉네임 같은 경우)
        MemberFormDTO memberFormDTO2 = new MemberFormDTO();
        memberFormDTO2.setEmail("test2@test.com");
        memberFormDTO2.setNick("test1");
        memberFormDTO2.setAddress("테스트시 테스트동");
        memberFormDTO2.setPassword("1234");

        Throwable e = Assertions.assertThrows(DuplicateMemberException.class, ()->{
            memberService.register(memberFormDTO1); //같은 이메일이 이미 있을때 에러 반환 test
        });

        //에러 메세지 검증
        Assertions.assertEquals(e.getMessage(),"중복된 이메일이 이미 존재합니다.");

        Throwable e2 = Assertions.assertThrows(DuplicateMemberException.class, ()->{
            memberService.register(memberFormDTO2); //같은 닉네임이 이미 있을때 에러 반환 test
        });

        //에러 메세지 검증
        Assertions.assertEquals(e2.getMessage(),"중복된 닉네임이 이미 존재합니다.");
    }
}