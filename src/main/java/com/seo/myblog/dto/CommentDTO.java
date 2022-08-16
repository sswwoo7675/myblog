package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {

    private String content; //댓글내용
    
    private Long postId;//댓글 단 포스트 번호

}
