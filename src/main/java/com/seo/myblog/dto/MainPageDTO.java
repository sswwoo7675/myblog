package com.seo.myblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
* 메인 페이지 상위 포스트 출력용 DTO
* */
@Getter
@Setter
@ToString
@Builder
public class MainPageDTO {
    private Long postId;

    private String title;

    private String writer;

    private String regTime;

    private String avatar;
}
