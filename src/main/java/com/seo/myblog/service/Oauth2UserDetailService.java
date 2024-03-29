package com.seo.myblog.service;

import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.dto.UserInfoDTO;
import com.seo.myblog.entity.Member;
import com.seo.myblog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


/*
DefaultOAuth2UserService 상속받아 소셜 로그인 서비스 구현
 */
@Service
@RequiredArgsConstructor
public class Oauth2UserDetailService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String client = userRequest.getClientRegistration().getClientName(); //소셜 로그인 사이트이름(Google 등) 저장

        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k,v)->{
            System.out.println(k + " = " + v);
        });

        String email = null;
        String nick = null;
        String picture = null;

        if(client.equals("Google")){
            email = oAuth2User.getAttribute("email");
            nick = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");
        }
        Member member = savedMember(email, nick, picture);//소셜 정보의 email과 name, picture을 포함하여 db에 Member 테이블에 저장

        UserInfoDTO userInfoDTO = new UserInfoDTO(member.getEmail(),member.getPassword(),member.getNick(),member.getRole().toString(),member.getAvatar(),oAuth2User.getAttributes());

        return userInfoDTO;

    }

    public Member savedMember(String email, String Nick, String picture){
        Optional<Member> chkMember = memberRepository.findByEmailAndIsSocial(email,true); //이미 소셜로그인으로 가입한 멤버가 있는지 확인

        if(chkMember.isPresent()){ //존재하면 Member db저장 없이 바로 반환
            return chkMember.get();
        }

        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setEmail(email);
        memberFormDTO.setPassword("social@$@#$KFFI#9");
        memberFormDTO.setNick(Nick); //MemberForm 정보 생성
        memberFormDTO.setAvatar(picture);

        Member member = Member.createMember(memberFormDTO,passwordEncoder); //Member 객체생성
        member.setIsSocial(true); //소셜로그인이므로 isSocial True로 설정함
        
        return memberRepository.save(member);

    }
}
