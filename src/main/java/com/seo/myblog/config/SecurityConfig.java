package com.seo.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    

    /*
    SecurityFilterChain을 빈으로 등록하여 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 설정 등 수행
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); //스프링시큐리티가 항상 세션을 사용하도록 설정

        //로그인, 로그아웃 관련 설정
        http.formLogin()
                .loginPage("/member/login")  //로그인 페이지 URL설정
                .defaultSuccessUrl("/blog/")  //로그인 성공 후 이동할 URL 설정
                .usernameParameter("email")  //로그인 시 user파라미터 설정
                .failureUrl("/member/login/error") //로그인 실패 시 이동할 URL 설정
                .and() 
                .logout() //로그아웃관련설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃 URL 설정
                .logoutSuccessUrl("/blog/"); //로그아웃 성공 후 이동할 URL 설정

        http.oauth2Login()
                .defaultSuccessUrl("/blog/"); //소셜로그인 활성화 및 성공시 URL 설정

        http.csrf().ignoringAntMatchers("/ajax/**");

        http.authorizeRequests()
                .mvcMatchers("/css/**","/js/**","/img/**","/ajax/**","/category/info").permitAll() //css js img등 정적파일 항상 접근 허용
                .mvcMatchers("/","/member/**","/blog/**", "/images/**","/test/**").permitAll() //기본 주소 항상 접근 허용
                .mvcMatchers("/admin/**").hasRole("ADMIN") //admin으로 시작하는 주소 admin권한을 가져야 접근 가능하도록 설정
                .anyRequest().authenticated(); //나머지 url에 대해서는 로그인 해야 접근 가능

        return http.build();
    }

    /*
    BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장하기 위해 BCryptPasswordEncoder를 빈으로 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); 
    }
}
