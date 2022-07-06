package com.seo.myblog.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserInfoDTO implements UserDetails, OAuth2User {
    private String email;
    private String password;

    private String nick;
    private String AUTHORITY;

    private Map<String, Object> attr;

    /*
    일반 로그인 용
     */
    public UserInfoDTO(String email, String password, String nick, String AUTHORITY) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.AUTHORITY = AUTHORITY;
    }

    /*
    소셜 로그인용
     */
    public UserInfoDTO(String email, String password, String nick, String AUTHORITY, Map<String, Object> attr) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.AUTHORITY = AUTHORITY;
        this.attr = attr;
    }

    /*
    UserDetails구현부
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("ROLE_" + AUTHORITY));
        return auth;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
    OAuth2User구현
     */

    @Override
    public Map<String, Object> getAttributes() {
        return attr;
    }

    @Override
    public String getName() {
        String sub = attr.get("sub").toString();
        return sub; //소셜 사이트에서 제공하는 sub 정보 반환하게 함
    }

    /*
    커스텀 부분
     */
    public String getNick(){
        return nick;
    }
}
