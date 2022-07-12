package com.seo.myblog.config;

import com.seo.myblog.dto.UserInfoDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

/*로그인한 사용자의 닉네임을 등록자와 수정자로 지정*/
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //인증정보 가져오기
        String nick = "";

        if(authentication!=null){ //인증정보가 존재하면 닉네임을 가져옴
            nick = ((UserInfoDTO)authentication.getPrincipal()).getNick();
        }

        return Optional.of(nick);
    }
}
