package com.seo.myblog.config;

import com.seo.myblog.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

/*로그인한 사용자의 닉네임을 등록자와 수정자로 지정*/
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //인증정보 가져오기
        String nick = "";

        if(authentication == null || authentication.getName().equals("anonymousUser")){ //인증정보가 존재하면 닉네임을 가져옴
            return null;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) authentication.getPrincipal();
        return Optional.of(userInfoDTO.getNick());
    }
}
