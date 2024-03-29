package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/*
* 포스트 정보 저장
* */
@Getter
@Setter
@ToString
public class PostDTO {
    private Long postId; //포스트Id;

    private String title; //제목

    private String hook_text; //요약

    private String content; //본문

    private String headImgUrl; //헤드 이미지 url

    private String attachedFileUrl; //첨부파일 url

    private String attachedFileName; //첨부파일 원 파일명;

    private String writer; //작성자

    private List<String> tags; //태그

    private String postDate; //작성시간

    private CategoryDTO categoryDTO; //카테고리 정보

}
