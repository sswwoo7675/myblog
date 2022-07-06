package com.seo.myblog.entity;

import com.seo.myblog.constant.MemberRole;
import com.seo.myblog.dto.MemberFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter @ToString
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //기본키

    @Column(unique = true)
    private String email; //이메일

    private String password; //패스워드

    @Column(unique = true)
    private String nick; //닉네임

    private String address; //주소

    @Enumerated(EnumType.STRING)
    private MemberRole role; //회원권한(String 형식으로 DB저장)

    //폼 데이터 정보로부터 Member Entity 생성
    public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();

        member.email = memberFormDTO.getEmail();
        member.password = passwordEncoder.encode(memberFormDTO.getPassword()); //패스워드는 passwordEncoder로 해시 후 설정
        member.nick = memberFormDTO.getNick();
        member.address = memberFormDTO.getAddress();
        member.role = MemberRole.USER; //일반 유저 권한으로 설정

        return member;
    }
}
