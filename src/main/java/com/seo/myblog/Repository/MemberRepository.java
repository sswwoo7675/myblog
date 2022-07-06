package com.seo.myblog.Repository;

import com.seo.myblog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByEmail(String email); //이메일로 회원 검색
    
    Optional<Member> findByNick(String Nick); //닉네임으로 회원 검색

}
