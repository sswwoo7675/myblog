package com.seo.myblog.config;

/*Auditing 기능 사용*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

   /* @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl(); //AuditorAware를 직접 구현한 객체로 이용
    }*/

}
