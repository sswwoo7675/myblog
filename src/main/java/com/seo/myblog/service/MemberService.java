package com.seo.myblog.service;

import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.dto.UserInfoDTO;
import com.seo.myblog.entity.Member;
import com.seo.myblog.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { //UserDetailsService 인터페이스 상속받아 로그인 기능 구현
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입 메서드
    public Long register(MemberFormDTO memberFormDTO) throws DuplicateMemberException{
        validateDuplicateMember(memberFormDTO.getEmail(), memberFormDTO.getNick()); //중복 회원 확인 메서드 실행

        Member member =  Member.createMember(memberFormDTO, passwordEncoder); //Member엔티티 생성

        return memberRepository.save(member).getId(); //db에 저장
    }

    //중복 회원 확인 메서드(이메일과 닉네임 체크)
    public void validateDuplicateMember(String email, String nick) throws DuplicateMemberException{
        Optional<Member> findMember1 = memberRepository.findByEmail(email); //이메일로 Member찾기
        Optional<Member> findMember2 = memberRepository.findByNick(nick); //닉네임으로 Member찾기

        if(findMember1.isPresent()){
            throw new DuplicateMemberException("중복된 이메일이 이미 존재합니다.");
        } else if (findMember2.isPresent()) {
            throw new DuplicateMemberException("중복된 닉네임이 이미 존재합니다.");
        }
    }

    /*로그인 기능 구현 : UserDetails 인터페이스 구현한 User 클래스 반환*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //db에서 이메일로 Member를 찾고 없을경우 UsernameNotFoundException 예외발생
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));

        /*return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();*/

        return new UserInfoDTO(member.getEmail(),member.getPassword(),member.getNick(),member.getRole().toString());

    }
}
