package com.seo.myblog;

import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.dto.MemberFormDTO;
import com.seo.myblog.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class MemberRepositoryTest {
    
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

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
    @DisplayName("이메일로 Member 찾기 테스트")
    public void findByEmailTest(){
        Member member = createMember(); //테스트용 멤버 생성
        String prevEmail = member.getEmail(); //db저장후 검증위해 이메일 저장
        String prevNick = member.getNick(); //db저장후 검증위해 닉네임 저장

        memberRepository.save(member);
        em.flush();
        em.clear(); //멤버 저장후 db에 저장, 영속성 컨텍스트 초기화

        Member savedMember = memberRepository.findByEmail(prevEmail).orElseThrow(EntityNotFoundException::new); //저장해 둔 이메일 정보로 Member찾기
                                                                                                                // 못 찾으면 EntityNotFoundException 발생시킴

        Assertions.assertEquals(prevNick, savedMember.getNick()); //미리 저장해둔 닉네임이랑 불러온 Member 닉네임이랑 일치한지 검증
    }

    @Test
    @DisplayName("닉네임으로 Member 찾기 테스트")
    public void findByNickTest(){
        Member member = createMember(); //테스트용 멤버 생성
        String prevEmail = member.getEmail(); //db저장후 검증위해 이메일 저장
        String prevNick = member.getNick(); //db저장후 검증위해 닉네임 저장

        memberRepository.save(member);
        em.flush();
        em.clear(); //멤버 저장후 db에 저장, 영속성 컨텍스트 초기화

        Member savedMember = memberRepository.findByNick(prevNick).orElseThrow(EntityNotFoundException::new); //저장해 둔 닉네임 정보로 Member찾기
                                                                                                            // 못 찾으면 EntityNotFoundException 발생시킴

        Assertions.assertEquals(prevEmail, savedMember.getEmail()); //미리 저장해둔 이메일이랑 불러온 Member 이메일이랑 일치한지 검증
    }
}